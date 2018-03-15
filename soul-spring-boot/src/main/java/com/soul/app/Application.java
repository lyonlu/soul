package com.soul.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author sumy
 */
@SpringBootApplication
public class Application {

    /**
     * @param args
     */
    public static void main(String[] args) {

        /* SpringApplication.run(Application.class,args); */
        new SpringApplicationBuilder().sources(Application.class).run(args);
    }
}
