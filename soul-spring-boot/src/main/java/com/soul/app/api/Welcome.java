package com.soul.app.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sumy
 * @date: 2018/3/13
 * @since: 1.0.0
 */
@RestController
public class Welcome {

    @RequestMapping("/index")
    public String home() {
        return "Welcome to spring boot world!";
    }
}
