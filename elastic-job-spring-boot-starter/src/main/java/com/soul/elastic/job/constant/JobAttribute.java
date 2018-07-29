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

package com.soul.elastic.job.constant;

/**
 * @author: sumy
 * @date: 2018/7/28 15:41
 * @since: 1.0.0
 */
public interface JobAttribute {

    String CRON = "cron";


    String SHARDING_TOTAL_COUNT = "shardingTotalCount";


    String SHARDING_ITEM_PARAMETERS = "shardingItemParameters";


    String JOB_PARAMETER = "jobParameter";


    String MONITOR_EXECUTION = "monitorExecution";


    String MONITOR_PORT = "monitorPort";


    String FAILOVER = "failover";


    String MAX_TIME_DIFF_SECONDS = "maxTimeDiffSeconds";


    String MISFIRE = "misfire";


    String JOB_SHARDING_STRATEGY_CLASS = "jobShardingStrategyClass";


    String DESCRIPTION = "description";


    String DISABLED = "disabled";


    String OVERWRITE = "overwrite";


    String LISTENER = "listener";


    String DISTRIBUTED_LISTENER = "distributedListener";


    String DISTRIBUTED_LISTENER_STARTED_TIMEOUT_MILLISECONDS = "startedTimeoutMilliseconds";


    String DISTRIBUTED_LISTENER_COMPLETED_TIMEOUT_MILLISECONDS = "completedTimeoutMilliseconds";


    String EXECUTOR_SERVICE_HANDLER = "executorServiceHandler";


    String JOB_EXCEPTION_HANDLER = "jobExceptionHandler";


    String EVENT_TRACE_RDB_DATA_SOURCE = "eventTraceRdbDataSource";


    String RECONCILE_INTERVAL_MINUTES = "reconcileIntervalMinutes";


    String SCRIPT_COMMAND_LINE = "scriptCommandLine";


    String STREAMING_PROCESS = "streamingProcess";
}
