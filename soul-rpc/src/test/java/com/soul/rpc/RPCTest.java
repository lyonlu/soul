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
public class RPCTest {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RPCTest.class);

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
