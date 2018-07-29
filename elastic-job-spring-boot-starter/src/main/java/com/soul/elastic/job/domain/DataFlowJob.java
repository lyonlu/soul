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

package com.soul.elastic.job.domain;

import com.soul.elastic.job.enums.JobTypeEnum;
import lombok.Data;

/**
 * @author: sumy
 * @date: 2018/7/28 21:13
 * @since: 1.0.0
 */
@Data
public class DataFlowJob extends Job {

    /**
     * 是否流式处理数据
     *
     * <p>如果流式处理数据, 则fetchData不返回空结果将持续执行作业<p>
     *
     * <p>如果非流式处理数据, 则处理数据完成后作业结束<p>
     *
     * @return
     */
    private boolean streamingProcess = false;

    /**
     *
     */
    public DataFlowJob() {
        super();
        this.setJobType(JobTypeEnum.dataFlow.getName());
    }
}
