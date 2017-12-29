package com.soul.config;

import com.soul.rpc.ProxyFactory;

import java.lang.reflect.InvocationHandler;

/**
 * @author: sumy
 * @date: 2017/12/29
 * @since: 1.0.0
 */
public class ReferenceConfig<T> {

    /**
     *
     */
    private Class<?> interfaceClass;
    /**
     * 接口代理类引用
     */
    private transient volatile T ref;

    /**
     * 处理类
     */
    private InvocationHandler handler;

    public synchronized T get() {
        if (null == ref) {
            init();
        }
        return ref;
    }

    /**
     *
     */
    private void init() {
        ref = new ProxyFactory(interfaceClass, handler).getProxyObject();
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
