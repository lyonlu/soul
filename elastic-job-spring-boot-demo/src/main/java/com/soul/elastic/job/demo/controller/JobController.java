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

package com.soul.elastic.job.demo.controller;

import com.soul.elastic.job.domain.Job;
import com.soul.elastic.job.domain.ScriptJob;
import com.soul.elastic.job.domain.SimpleJob;
import com.soul.elastic.job.enums.JobTypeEnum;
import com.soul.elastic.job.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: sumy
 * @date: 2018/7/28 21:56
 * @since: 1.0.0
 */
@RestController
@Slf4j
public class JobController {

    @Autowired(required = false)
    private JobService jobService;


    /**
     * 添加动态任务（适用于脚本逻辑已存在的情况，只是动态添加了触发的时间）
     *
     * @param job 任务信息
     * @return
     */
    @PostMapping("/job")
    public Object addJob(@RequestBody Job job) {

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("status", true);

        if (!StringUtils.hasText(job.getJobName())) {
            result.put("status", false);
            result.put("message", "name not null");
            return result;
        }


        if (!StringUtils.hasText(job.getCron())) {
            result.put("status", false);
            result.put("message", "cron not null");
            return result;
        }

        if (!StringUtils.hasText(job.getJobType())) {
            result.put("status", false);
            result.put("message", "getJobType not null");
            return result;

        }


        if ("ScriptJob".equals(job.getJobType())) {

            /*if (!StringUtils.hasText(((ScriptJob) job).getScriptCommandLine())) {
                result.put("status", false);
                result.put("message", "scriptCommandLine not null");
                return result;

            }*/

        } else {

            if (!StringUtils.hasText(job.getJobClass())) {
                result.put("status", false);
                result.put("message", "jobClass not null");
                return result;
            }

        }


        try {

            if (JobTypeEnum.simple.getName().equals(job.getJobType())) {
                jobService.addJob(job);
            }

        } catch (Exception e) {
            log.error("add job error:", e);
            result.put("status", false);

            result.put("message", e.getMessage());

        }

        return result;

    }


    /**
     * 删除动态注册的任务（只删除注册中心中的任务信息）
     *
     * @param jobName 任务名称
     * @throws Exception
     */

    @GetMapping("/job/remove")
    public Object removeJob(String jobName) {

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("status", true);

        try {

            jobService.removeJob(jobName);

        } catch (Exception e) {

            result.put("status", false);

            result.put("message", e.getMessage());

        }

        return result;

    }

}
