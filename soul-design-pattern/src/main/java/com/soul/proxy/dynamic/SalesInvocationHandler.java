package com.soul.proxy.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: sumy
 * @date: 2018/1/5
 * @since: 1.0.0
 */
public class SalesInvocationHandler implements InvocationHandler {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SalesInvocationHandler.class);

    private Object delegate;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        logger.info("很高兴为你服务");
        Object result = method.invoke(delegate, args);
        logger.info("欢迎您下次光临");
        return result;
    }

    public <T> T bind(Object delegate) {
        this.delegate = delegate;
        return (T) Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(), this);
    }
}
