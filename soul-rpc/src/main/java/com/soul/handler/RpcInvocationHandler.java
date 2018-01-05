package com.soul.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: sumy
 * @date: 2017/12/31 14:36
 * @since: 1.0.0
 */
public abstract class RpcInvocationHandler implements InvocationHandler {

    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(RpcInvocationHandler.class);
    /**
     * 代理类委托对象
     */
    protected Object delegate;

    /**
     * @param delegate
     */
    public RpcInvocationHandler(Object delegate) {
        this.delegate = delegate;
    }

    /**
     *
     */
    public RpcInvocationHandler() {

    }

    /**
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        logger.info("method:{}", method.toString());
        logger.info("encode:");
        //Object result = method.invoke(delegate, args);
        logger.info("send request");
        logger.info("decode request result");
        return null;
    }
}

