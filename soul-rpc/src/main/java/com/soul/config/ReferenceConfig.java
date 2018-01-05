package com.soul.config;

import com.soul.handler.RpcInvocationHandler;
import com.soul.rpc.MenuService;
import com.soul.rpc.ProxyFactory;

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
        ref = new ProxyFactory(interfaceClass).getProxyObject();
    }

    public Class<?> getInterfaceClass() {

        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {

        this.interfaceClass = interfaceClass;
    }
}
