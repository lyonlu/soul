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

package com.soul.elastic.job.enums;

import lombok.Getter;

/**
 * @author: sumy
 * @date: 2018/7/28 18:35
 * @since: 1.0.0
 */
@Getter
public enum JobTypeEnum {

    /**
     *
     */
    simple("SimpleJob"),
    /**
     *
     */
    dataFlow("DataflowJob"),
    /**
     *
     */
    script("ScriptJob");

    /**
     * job 类型名称
     */
    private String name;

    /**
     * @param name job类型
     */
    JobTypeEnum(String name) {
        this.name = name;
    }
}
