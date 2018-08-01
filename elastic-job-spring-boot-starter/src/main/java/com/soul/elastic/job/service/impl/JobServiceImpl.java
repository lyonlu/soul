/*
 *
 *  Copyright (c) 2017-2018 Soul, sumy  All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *   without limitation  the rights to  use, copy, modify,  merge, publish,
 *   distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *   permit persons to whom the Software  is furnished to do so, subject to
 *   the following conditions:
 *
 *   The  above  copyright  notice  and  this permission  notice  shall  be
 *   included in all copies or substantial portions of the Software.
 *
 */

package com.soul.elastic.job.service.impl;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.soul.elastic.job.domain.DataFlowJob;
import com.soul.elastic.job.domain.Job;
import com.soul.elastic.job.domain.ScriptJob;
import com.soul.elastic.job.enums.JobTypeEnum;
import com.soul.elastic.job.parse.JobTypeConfigBuilder;
import com.soul.elastic.job.service.JobService;
import com.soul.elastic.job.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: sumy
 * @date: 2018/7/28 19:15
 * @since: 1.0.0
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService {

    /**
     *
     */
    private static final String SPRING_JOB_SCHEDULER = "SpringJobScheduler";

    private Map<String, AtomicInteger> JOB_ADD_COUNT = new ConcurrentHashMap<String, AtomicInteger>();

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    /**
     * 任务变化时通知zk其他节点
     */
    @Override
    public void notifyJobRegister() {

        CuratorFramework client = zookeeperRegistryCenter.getClient();
        PathChildrenCache childrenCache = new PathChildrenCache(client, "/", true);
        PathChildrenCacheListener childrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                ChildData data = event.getData();
                switch (event.getType()) {
                    case CHILD_ADDED:
                        String config = new String(client.getData().forPath(data.getPath() + "/config"));
                        Job job = JSON.parseObject(config, Job.class);
                        // 启动时任务会添加数据触发事件，这边需要去掉第一次的触发，不然在控制台进行手动触发任务会执行两次任务
                        if (null == job) {
                            break;
                        }
                        if (!JOB_ADD_COUNT.containsKey(job.getJobName())) {
                            JOB_ADD_COUNT.put(job.getJobName(), new AtomicInteger());
                        }
                        int count = JOB_ADD_COUNT.get(job.getJobName()).incrementAndGet();
                        if (count > 1) {
                            addJob(job);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        childrenCache.getListenable().addListener(childrenCacheListener);
        try {
            childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        } catch (Exception e) {
            log.error("zk notify error:", e);
        }
    }


    /**
     * 添加作业
     *
     * @param job 作业信息
     */
    @Override
    public void addJob(Job job) {

        // 核心配置
        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(job.getJobName(), job.getCron(), job.getShardingTotalCount())
                .shardingItemParameters(job.getShardingItemParameters())
                .description(job.getDescription())
                .failover(job.isFailover())
                .jobParameter(job.getJobParameter())
                .misfire(job.isMisfire())
                .jobProperties(JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), job.getJobProperties().getJobExceptionHandler())
                .jobProperties(JobProperties.JobPropertiesEnum.EXECUTOR_SERVICE_HANDLER.getKey(), job.getJobProperties().getExecutorServiceHandler())
                .build();

        JobTypeConfiguration jobTypeConfig = JobTypeConfigBuilder.getJobTypeConfiguration(job.getJobType(), coreConfig, job.getJobClass(), job);

        LiteJobConfiguration liteJobConfig = LiteJobConfiguration.newBuilder(jobTypeConfig)
                .overwrite(job.isOverwrite())
                .disabled(job.isDisabled())
                .monitorPort(job.getMonitorPort())
                .monitorExecution(job.isMonitorExecution())
                .maxTimeDiffSeconds(job.getMaxTimeDiffSeconds())
                .jobShardingStrategyClass(job.getJobShardingStrategyClass())
                .reconcileIntervalMinutes(job.getReconcileIntervalMinutes())
                .build();

        List<BeanDefinition> elasticJobListeners = getTargetElasticJobListeners(job);
        // 构建SpringJobScheduler对象来初始化任务
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(SpringJobScheduler.class);

        factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);

        if (JobTypeEnum.script.getName().equals(job.getJobType())) {
            factory.addConstructorArgValue(null);
        } else {
            BeanDefinitionBuilder rdbFactory = BeanDefinitionBuilder.rootBeanDefinition(job.getJobClass());
            factory.addConstructorArgValue(rdbFactory.getBeanDefinition());
        }
        factory.addConstructorArgValue(zookeeperRegistryCenter);
        factory.addConstructorArgValue(liteJobConfig);

        // 任务执行日志数据源，以名称获取
        if (StringUtils.hasText(job.getEventTraceRdbDataSource())) {
            BeanDefinitionBuilder rdbFactory = BeanDefinitionBuilder.rootBeanDefinition(JobEventRdbConfiguration.class);
            rdbFactory.addConstructorArgReference(job.getEventTraceRdbDataSource());
            factory.addConstructorArgValue(rdbFactory.getBeanDefinition());
        }

        factory.addConstructorArgValue(elasticJobListeners);
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) ApplicationContextUtils.getContext().getAutowireCapableBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition(SPRING_JOB_SCHEDULER, factory.getBeanDefinition());
        SpringJobScheduler springJobScheduler = (SpringJobScheduler) ApplicationContextUtils.getContext().getBean(SPRING_JOB_SCHEDULER);
        springJobScheduler.init();

        log.info("job->> jobName:{},jobClass:{} add success", new Object[]{job.getJobName(), job.getJobClass()});

    }

    /**
     * 删除作业
     *
     * @param jobName 作业名称
     */
    @Override
    public void removeJob(String jobName) {

        CuratorFramework client = zookeeperRegistryCenter.getClient();
        try {
            client.delete().deletingChildrenIfNeeded().forPath("/" + jobName);
        } catch (Exception e) {
            log.error("delete job error:", e);
        }
    }


    private List<BeanDefinition> getTargetElasticJobListeners(Job job) {

        List<BeanDefinition> result = new ManagedList<BeanDefinition>(2);
        String listeners = job.getListener();
        if (StringUtils.hasText(listeners)) {
            BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(listeners);
            factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            result.add(factory.getBeanDefinition());
        }
        String distributedListeners = job.getDistributedListener();
        long startedTimeoutMilliseconds = job.getStartedTimeoutMilliseconds();
        long completedTimeoutMilliseconds = job.getCompletedTimeoutMilliseconds();

        if (StringUtils.hasText(distributedListeners)) {
            BeanDefinitionBuilder factory;
            factory = BeanDefinitionBuilder.rootBeanDefinition(distributedListeners);
            factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            factory.addConstructorArgValue(startedTimeoutMilliseconds);
            factory.addConstructorArgValue(completedTimeoutMilliseconds);
            result.add(factory.getBeanDefinition());
        }

        return result;
    }
}
