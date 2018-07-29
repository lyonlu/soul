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

package com.soul.elastic.job.service;

import com.soul.elastic.job.domain.Job;

/**
 * @author: sumy
 * @date: 2018/7/28 19:13
 * @since: 1.0.0
 */
public interface JobService {

    /**
     * 任务变化时通知zk其他节点
     */
    void notifyJobRegister();

    /**
     * 添加作业
     *
     * @param job 作业信息
     */
    void addJob(Job job);

    /**
     * 删除作业
     *
     * @param jobName 作业名称
     */
    void removeJob(String jobName);
}
