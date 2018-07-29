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

package com.soul.rpc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author: sumy
 * @date: 2017/12/31 17:28
 * @since: 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:WEB-INF/spring/applicationContext.xml")
public class RPCTestA {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RPCTestA.class);

    @Resource
    private MenuService menuService;

    @Test
    public void sayHello() {

        menuService.sayHello();
    }

    @Test
    public void traditionSayHello() {

        MenuService menuService = new ProxyFactory(MenuService.class).getProxyObject();
        menuService.sayHello();

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            logger.info("测试程序异常,{}", e.getMessage(), e);
        }
    }

}
