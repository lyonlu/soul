package com.soul.rpc;

import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Resource
    private MenuService menuService;

    @Test
    public void sayHello() {
        menuService.sayHello();
    }
}
