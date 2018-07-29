/*
 *
 *  Copyright (c) 2017-2018 Soul, sumy  All rights reserved.
 *
 *  Permission is hereby granted, free  of charge, to any person obtaining
 *  a  copy  of this  software  and  associated  documentation files  (the
 *  "Software"), to  deal in  the Software without  restriction, including
 *   without limitation  the rights to  use, copy, modify,  merge, publish,
 *   distribute,  sublicense, and/or sell  copies of  the Software,  and to
 *   permit persons to whom the Software  is furnished to do so, subject to
 *   the following conditions:
 *
 *   The  above  copyright  notice  and  this permission  notice  shall  be
 *   included in all copies or substantial portions of the Software.
 *
 */

package com.soul.elastic.job.demo;

import com.soul.elastic.job.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = ElasticJobSpringBootDemoApplication.basePackages)
@EnableElasticJob
public class ElasticJobSpringBootDemoApplication {

    final static String basePackages = "com.soul.elastic.job";

    public static void main(String[] args) {
        SpringApplication.run(ElasticJobSpringBootDemoApplication.class, args);
    }
}