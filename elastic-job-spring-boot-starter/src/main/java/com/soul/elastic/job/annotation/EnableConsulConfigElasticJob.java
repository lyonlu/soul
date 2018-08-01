package com.soul.elastic.job.annotation;

import com.soul.elastic.job.autoconfigure.JobParserAutoConfiguration;
import com.soul.elastic.job.parse.consul.ConsulJobConfig;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author: sumy
 * @date: 2018/8/1
 * @since: 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ConsulJobConfig.class})
public @interface EnableConsulConfigElasticJob {
}
