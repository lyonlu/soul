package com.soul.config;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author: sumy
 * @date: 2017/12/31 15:26
 * @since: 1.0.0
 */
public class ReferenceBean<T> extends ReferenceConfig<T> implements FactoryBean {

    @Override
    public Object getObject() {
        return get();
    }

    @Override
    public Class<?> getObjectType() {
        return getInterfaceClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
