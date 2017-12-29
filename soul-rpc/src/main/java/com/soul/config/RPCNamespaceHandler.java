package com.soul.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author: sumy
 * @date: 2017/12/31 16:19
 * @since: 1.0.0
 */
public class RPCNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("reference", new RPCBeanDefinitionParser());
    }
}
