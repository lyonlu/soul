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

package com.soul.elastic.job.demo.job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.soul.elastic.job.annotation.ElasticJobConfig;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: sumy
 * @date: 2018/7/28 23:30
 * @since: 1.0.0
 */
@Slf4j
@ElasticJobConfig(name = "soulSimpleJob", cron = "0/10 * * * * ?", shardingItemParameters = "0=0,1=1,2=2", shardingTotalCount = 3,
        failover = true, overwrite = true, description = "简单任务")
public class SoulSimpleJob implements SimpleJob {

    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {

        log.info("开始执行作业");
        String shardParameter = shardingContext.getShardingParameter();
        log.info("分片参数：{}", new Object[]{shardParameter});
        int value = Integer.parseInt(shardParameter);
        for (int i = 0; i < 5; i++) {
            if (i % 2 == value) {
                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                log.info("{} :开始执行简单任务 {}", new Object[]{time, i});
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("任务执行异常", e);
                }
            }
        }
    }
}
