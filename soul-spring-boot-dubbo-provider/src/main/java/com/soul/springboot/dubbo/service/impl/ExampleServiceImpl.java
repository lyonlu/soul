package com.soul.springboot.dubbo.service.impl;


import com.soul.springboot.dubbo.api.service.ExampleService;
import org.springframework.stereotype.Service;

/**
 * @author: sumy
 * @date: 2018/6/8
 * @since: 1.0.0
 */
@Service
public class ExampleServiceImpl implements ExampleService {

    @Override
    public void hello(String name) {
        System.out.println("hello " + name);
    }
}
