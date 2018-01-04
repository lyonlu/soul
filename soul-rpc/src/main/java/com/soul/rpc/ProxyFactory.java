package com.soul.rpc;

import com.soul.handler.RpcInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author: sumy
 * @date: 2017/12/29
 * @since: 1.0.0
 */
public class ProxyFactory {

    /**
     *
     */
    private Class interfaceClass;

    /**
     *
     */
    private InvocationHandler handler;

    /**
     * @param interfaceClass class namespace
     */
    public ProxyFactory(Class interfaceClass, InvocationHandler handler) {
        this.interfaceClass = interfaceClass;
        this.handler = handler;

    }

    /**
     * @param <T>
     * @return
     */
    public <T> T getProxyObject() {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{interfaceClass}, handler);
    }
}
