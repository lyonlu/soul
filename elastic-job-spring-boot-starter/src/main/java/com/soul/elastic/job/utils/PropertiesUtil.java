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

package com.soul.elastic.job.utils;

import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * @author: sumy
 * @date: 2018/7/28 17:35
 * @since: 1.0.0
 */
public class PropertiesUtil {

    /**
     * job 配置前缀
     */
    private static final String prefix = "elastic.job.";

    public static Environment environment;

    static {
        environment = ApplicationContextUtils.getContext().getEnvironment();
    }

    /**
     * 获取配置中的任务属性值，environment没有就用注解中的值
     *
     * @param jobName      任务名称
     * @param fieldName    属性名称
     * @param defaultValue 默认值
     * @return
     */
    public static String getEnvStringValue(String jobName, String fieldName, String defaultValue) {

        String value = getEnvValue(getKey(jobName, fieldName));
        if (StringUtils.hasText(value)) {
            return value;
        }
        return defaultValue;
    }

    /**
     * 获取配置中的任务属性值，environment没有就用注解中的值
     *
     * @param jobName      任务名称
     * @param fieldName    属性名称
     * @param defaultValue 默认值
     * @return
     */
    public static int getEnvIntValue(String jobName, String fieldName, int defaultValue) {
        String value = getEnvValue(getKey(jobName, fieldName));
        if (StringUtils.hasText(value)) {
            return Integer.valueOf(value);
        }
        return defaultValue;
    }

    /**
     * 获取配置中的任务属性值，environment没有就用注解中的值
     *
     * @param jobName      任务名称
     * @param fieldName    属性名称
     * @param defaultValue 默认值
     * @return
     */
    public static long getEnvLongValue(String jobName, String fieldName, long defaultValue) {
        String value = getEnvValue(getKey(jobName, fieldName));
        if (StringUtils.hasText(value)) {
            return Long.valueOf(value);
        }
        return defaultValue;
    }

    /**
     * 获取配置中的任务属性值，environment没有就用注解中的值
     *
     * @param jobName      任务名称
     * @param fieldName    属性名称
     * @param defaultValue 默认值
     * @return
     */
    public static boolean getEnvBooleanValue(String jobName, String fieldName, boolean defaultValue) {
        String value = getEnvValue(getKey(jobName, fieldName));
        if (StringUtils.hasText(value)) {
            return Boolean.valueOf(value);
        }
        return defaultValue;
    }

    /**
     * 获取配置key
     *
     * @param args 组成key的元素
     * @return
     */
    private static String getKey(Object... args) {
        String key = prefix;
        for (int i = 0; i < args.length; i++) {
            key += args[i] + ".";
        }
        return key.substring(0, key.lastIndexOf("."));
    }

    /**
     * 获取key 对应的配置信息
     *
     * @param key 配置key
     * @return
     */
    private static String getEnvValue(String key) {
        return environment.getProperty(key);
    }

}
