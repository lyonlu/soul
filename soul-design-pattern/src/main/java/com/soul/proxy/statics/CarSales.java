package com.soul.proxy.statics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 私家车卖家
 *
 * @author: sumy
 * @date: 2018/1/5
 * @since: 1.0.0
 */
public class CarSales implements Sales {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CarSales.class);

    @Override
    public void sell() {
        logger.info("车就交给你了");
    }
}
