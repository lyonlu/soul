package com.soul.proxy.statics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: sumy
 * @date: 2018/1/5
 * @since: 1.0.0
 */
public class Customer {

    /**
     *
     */
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);

    public static void main(String[] args) {

        logger.info("我要买车");
        Sales agents = new Agents(new CarSales());
        agents.sell();
        logger.info("谢谢你,给你一个红包吧,买的车很满意");
    }
}
