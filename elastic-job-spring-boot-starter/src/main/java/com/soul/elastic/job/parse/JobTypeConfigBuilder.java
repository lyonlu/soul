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

package com.soul.elastic.job.parse;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.soul.elastic.job.enums.JobTypeEnum;

/**
 * @author: sumy
 * @date: 2018/7/29 14:04
 * @since: 1.0.0
 */
public class JobTypeConfigBuilder {

    /**
     * 根据作业类型 获取作业配置信息
     *
     * @param jobTypeName       作业类型
     * @param coreConfig        核心配置
     * @param jobClass          job 执行器
     * @param streamingProcess
     * @param scriptCommandLine
     * @return
     */
    public static JobTypeConfiguration getJobTypeConfiguration(String jobTypeName, JobCoreConfiguration coreConfig, String jobClass, boolean streamingProcess, String scriptCommandLine) {

        JobTypeConfiguration jobTypeConfig = null;
        if (jobTypeName.equals(JobTypeEnum.simple.getName())) {
            jobTypeConfig = new SimpleJobConfiguration(coreConfig, jobClass);
        }
        if (jobTypeName.equals(JobTypeEnum.dataFlow.getName())) {
            jobTypeConfig = new DataflowJobConfiguration(coreConfig, jobClass, streamingProcess);
        }
        if (jobTypeName.equals(JobTypeEnum.script.getName())) {
            jobTypeConfig = new ScriptJobConfiguration(coreConfig, scriptCommandLine);
        }
        return jobTypeConfig;
    }
}
