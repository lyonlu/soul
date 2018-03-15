package com.soul.app.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author: sumy
 * @date: 2018/3/14
 * @since: 1.0.0
 */
@Component
public class SoulApplicationRunner implements ApplicationRunner {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SoulApplicationRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info(SoulApplicationRunner.class.getName() + ":{},参数:{}", new Object[]{"ApplicationRunner", args.toString()});
    }
}
