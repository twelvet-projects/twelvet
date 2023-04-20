package com.twelvet.framework.core.annotation;

import com.twelvet.framework.core.config.JavaTimeModule;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: EnableFeignClients
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 自动加载类
@Import({ JavaTimeModule.class, FeignAutoConfiguration.class })
@EnableFeignClients
public @interface EnableTWTFeignClients {

	String[] value() default {};

	String[] basePackages() default { "com.twelvet" };

	Class<?>[] basePackageClasses() default {};

	Class<?>[] defaultConfiguration() default {};

	Class<?>[] clients() default {};

}
