package com.twelvet.framework.job.annotation;

import com.twelvet.framework.job.config.XxlJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启xxl-job调度器
 *
 * @author twelvet
 * @date 2025/05/04
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ XxlJobAutoConfiguration.class })
public @interface EnableXxlJob {

}