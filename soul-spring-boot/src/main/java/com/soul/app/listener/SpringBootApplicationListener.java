package com.soul.app.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

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
    }
}
