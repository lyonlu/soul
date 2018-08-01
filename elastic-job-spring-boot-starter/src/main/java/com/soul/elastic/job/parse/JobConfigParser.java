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

package com.soul.elastic.job.parse;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.soul.elastic.job.annotation.ElasticJobConfig;
import com.soul.elastic.job.constant.JobAttribute;
import com.soul.elastic.job.enums.JobTypeEnum;
import com.soul.elastic.job.service.JobService;
import com.soul.elastic.job.utils.PropertiesUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: sumy
 * @date: 2018/7/28 15:44
 * @since: 1.0.0
 */
@Configuration
@Component
@Data
@Slf4j
public class JobConfigParser implements ApplicationContextAware {

    @Resource
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Autowired(required = false)
    private JobService jobService;

    @Resource
    private PropertiesUtil propertiesUtil;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String, Object> jobBeans = applicationContext.getBeansWithAnnotation(ElasticJobConfig.class);

        for (Object jobBean : jobBeans.values()) {

            // 作业类型
            String jobTypeName = jobBean.getClass().getInterfaces()[0].getSimpleName();

            Class<?> jobClz = jobBean.getClass();
            String jobClass = jobClz.getName();

            // 作业配置信息
            ElasticJobConfig jobConfig = jobClz.getAnnotation(ElasticJobConfig.class);
            String jobName = jobConfig.name();

            // cron 表达式
            String cron = propertiesUtil.getEnvStringValue(jobName, JobAttribute.CRON, jobConfig.cron());
            // 作业分片参数
            String shardingItemParameters = propertiesUtil.getEnvStringValue(jobName, JobAttribute.SHARDING_ITEM_PARAMETERS, jobConfig.shardingItemParameters());
            // 作业描述信息
            String description = propertiesUtil.getEnvStringValue(jobName, JobAttribute.DESCRIPTION, jobConfig.description());
            // 作业参数
            String jobParameter = propertiesUtil.getEnvStringValue(jobName, JobAttribute.JOB_PARAMETER, jobConfig.jobParameter());
            // 作业异常处理器
            String jobExceptionHandler = propertiesUtil.getEnvStringValue(jobName, JobAttribute.JOB_EXCEPTION_HANDLER, jobConfig.jobExceptionHandler());
            // 作业执行器
            String executorServiceHandler = propertiesUtil.getEnvStringValue(jobName, JobAttribute.EXECUTOR_SERVICE_HANDLER, jobConfig.executorServiceHandler());
            // 作业分片策略
            String jobShardingStrategyClass = propertiesUtil.getEnvStringValue(jobName, JobAttribute.JOB_SHARDING_STRATEGY_CLASS, jobConfig.jobShardingStrategyClass());
            // 作业跟踪源
            String eventTraceRdbDataSource = propertiesUtil.getEnvStringValue(jobName, JobAttribute.EVENT_TRACE_RDB_DATA_SOURCE, jobConfig.eventTraceRdbDataSource());
            // script 作业脚本
            String scriptCommandLine = propertiesUtil.getEnvStringValue(jobName, JobAttribute.SCRIPT_COMMAND_LINE, jobConfig.scriptCommandLine());

            boolean failover = propertiesUtil.getEnvBooleanValue(jobName, JobAttribute.FAILOVER, jobConfig.failover());
            boolean misfire = propertiesUtil.getEnvBooleanValue(jobName, JobAttribute.MISFIRE, jobConfig.misfire());
            boolean overwrite = propertiesUtil.getEnvBooleanValue(jobName, JobAttribute.OVERWRITE, jobConfig.overwrite());
            boolean disabled = propertiesUtil.getEnvBooleanValue(jobName, JobAttribute.DISABLED, jobConfig.disabled());
            boolean monitorExecution = propertiesUtil.getEnvBooleanValue(jobName, JobAttribute.MONITOR_EXECUTION, jobConfig.monitorExecution());
            boolean streamingProcess = propertiesUtil.getEnvBooleanValue(jobName, JobAttribute.STREAMING_PROCESS, jobConfig.streamingProcess());

            int shardingTotalCount = propertiesUtil.getEnvIntValue(jobName, JobAttribute.SHARDING_TOTAL_COUNT, jobConfig.shardingTotalCount());
            int monitorPort = propertiesUtil.getEnvIntValue(jobName, JobAttribute.MONITOR_PORT, jobConfig.monitorPort());
            int maxTimeDiffSeconds = propertiesUtil.getEnvIntValue(jobName, JobAttribute.MAX_TIME_DIFF_SECONDS, jobConfig.maxTimeDiffSeconds());
            int reconcileIntervalMinutes = propertiesUtil.getEnvIntValue(jobName, JobAttribute.RECONCILE_INTERVAL_MINUTES, jobConfig.reconcileIntervalMinutes());

            // 核心配置
            JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                    .shardingItemParameters(shardingItemParameters)
                    .description(description)
                    .failover(failover)
                    .jobParameter(jobParameter)
                    .misfire(misfire)
                    .jobProperties(JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), jobExceptionHandler)
                    .jobProperties(JobProperties.JobPropertiesEnum.EXECUTOR_SERVICE_HANDLER.getKey(), executorServiceHandler)
                    .build();

            JobTypeConfiguration jobTypeConfig = JobTypeConfigBuilder.getJobTypeConfiguration(jobTypeName, coreConfig, jobClass, streamingProcess, scriptCommandLine);

            if (null == jobTypeConfig) {
                log.error("job config error: jobName:{},jobType:{}", new Object[]{jobName, jobTypeName});
                continue;
            }
            LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(jobTypeConfig)
                    .overwrite(overwrite)
                    .disabled(disabled)
                    .monitorPort(monitorPort)
                    .monitorExecution(monitorExecution)
                    .maxTimeDiffSeconds(maxTimeDiffSeconds)
                    .jobShardingStrategyClass(jobShardingStrategyClass)
                    .reconcileIntervalMinutes(reconcileIntervalMinutes)
                    .build();

            List<BeanDefinition> elasticJobListeners = getTargetElasticJobListeners(jobConfig);

            // 构建SpringJobScheduler对象来初始化任务
            BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(SpringJobScheduler.class);
            factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            if (JobTypeEnum.script.getName().equals(jobTypeName)) {
                factory.addConstructorArgValue(null);
            } else {
                factory.addConstructorArgValue(jobBean);
            }
            factory.addConstructorArgValue(zookeeperRegistryCenter);
            factory.addConstructorArgValue(liteJobConfiguration);

            // 任务执行日志数据源，以名称获取
            if (StringUtils.hasText(eventTraceRdbDataSource)) {

                BeanDefinitionBuilder rdbFactory = BeanDefinitionBuilder.rootBeanDefinition(JobEventRdbConfiguration.class);
                rdbFactory.addConstructorArgReference(eventTraceRdbDataSource);
                factory.addConstructorArgValue(rdbFactory.getBeanDefinition());
            }

            factory.addConstructorArgValue(elasticJobListeners);

            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

            defaultListableBeanFactory.registerBeanDefinition("SpringJobScheduler", factory.getBeanDefinition());

            SpringJobScheduler springJobScheduler = (SpringJobScheduler) applicationContext.getBean("SpringJobScheduler");

            springJobScheduler.init();

            log.info("job->> jobName:{},jobClass:{} init success", new Object[]{jobName, jobClass});

        }
        //开启任务监听,当有任务添加时，监听zk中的数据增加，自动在其他节点也初始化该任务
        if (jobService != null) {

            jobService.notifyJobRegister();

        }
    }

    /**
     * @param config
     * @return
     */
    private List<BeanDefinition> getTargetElasticJobListeners(ElasticJobConfig config) {

        List<BeanDefinition> beanDefinitionList = new ManagedList<BeanDefinition>(2);

        String listeners = propertiesUtil.getEnvStringValue(config.name(), JobAttribute.LISTENER, config.listener());
        if (StringUtils.hasText(listeners)) {
            BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(listeners);
            factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            beanDefinitionList.add(factory.getBeanDefinition());
        }

        String distributedListeners = propertiesUtil.getEnvStringValue(config.name(), JobAttribute.DISTRIBUTED_LISTENER, config.distributedListener());
        long startedTimeoutMilliseconds = propertiesUtil.getEnvLongValue(config.name(), JobAttribute.DISTRIBUTED_LISTENER_STARTED_TIMEOUT_MILLISECONDS, config.startedTimeoutMilliseconds());
        long completedTimeoutMilliseconds = propertiesUtil.getEnvLongValue(config.name(), JobAttribute.DISTRIBUTED_LISTENER_COMPLETED_TIMEOUT_MILLISECONDS, config.completedTimeoutMilliseconds());

        if (StringUtils.hasText(distributedListeners)) {
            // 分布式任务监听
            BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(distributedListeners);
            factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            factory.addConstructorArgValue(startedTimeoutMilliseconds);
            factory.addConstructorArgValue(completedTimeoutMilliseconds);
            beanDefinitionList.add(factory.getBeanDefinition());

        }

        return beanDefinitionList;

    }

}
