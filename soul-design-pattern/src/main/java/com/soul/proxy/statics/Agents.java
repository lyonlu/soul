package com.soul.proxy.statics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: sumy
 * @date: 2018/1/5
 * @since: 1.0.0
 */
public class Agents implements Sales {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Agents.class);

    /**
     * 车卖主
     */
    private Sales sales;

    /**
     * @param sales
     */
    public Agents(Sales sales) {
        this.sales = sales;
    }

    @Override
    public void sell() {
        logger.info("欢迎光临,我给你个优惠价");
        sales.sell();
        logger.info("收取提成,欢迎下次光临");

    }
}
