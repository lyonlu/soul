package com.soul.app.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author: sumy
 * @date: 2018/3/14
 * @since: 1.0.0
 */
@Component
public class SoulCommandLineRunner implements CommandLineRunner {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SoulCommandLineRunner.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info(SoulCommandLineRunner.class.getName() + ":{}", new Object[]{"CommandLineRunner"});
    }
}
