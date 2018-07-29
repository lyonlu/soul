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

package com.soul.elastic.job.cloud.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @author: sumy
 * @date: 2018/3/14
 * @since: 1.0.0
 */
public class SpringBootApplicationListener implements ApplicationListener {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SpringBootApplicationListener.class);

    @Resource
    private Environment env;

    /**
     * Event 参数类型如下：
     * 1、ApplicationStartingEvent
     * 2、ApplicationEnvironmentPreparedEvent
     * 3、ApplicationPreparedEvent
     * 4、ApplicationStartedEvent
     * 5、ApplicationReadyEvent
     * 6、ApplicationFailedEvent
     *
     * @param applicationEvent Event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        logger.info("Application Listener:{}", applicationEvent.toString());
        if (applicationEvent instanceof ApplicationReadyEvent) {
            env = ((ApplicationReadyEvent) applicationEvent).getApplicationContext().getEnvironment();
            logger.info("jobType:{}", new Object[]{env.getProperty("jobtype")});
        }
    }
}
