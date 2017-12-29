package com.soul.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: sumy
 * @date: 2017/12/31 16:44
 * @since: 1.0.0
 */
public class MenuServiceImpl implements MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Override
    public void sayHello() {
        logger.info("Hello world!");
    }
}
