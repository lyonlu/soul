package com.soul.rpc;

import com.soul.handler.RpcInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: sumy
 * @date: 2017/12/29
 * @since: 1.0.0
 */
public class ProxyFactory extends RpcInvocationHandler {

    /**
     *
     */
    private Class interfaceClass;

    /**
     * @param interfaceClass
     * @param handler
     */
    public ProxyFactory(Class interfaceClass, InvocationHandler handler) {

        this.interfaceClass = interfaceClass;
    }

    /**
     * @param <T>
     * @return
     */
    public <T> T getProxyObject() {

        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{interfaceClass}, this);
    }
}
