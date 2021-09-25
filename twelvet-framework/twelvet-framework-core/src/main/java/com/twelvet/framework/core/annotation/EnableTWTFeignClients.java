package com.twelvet.framework.core.annotation;

import com.twelvet.framework.core.config.ApplicationConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: EnableFeignClients
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 开启资源服务安全认证
@EnableResourceServer
// 自动加载类
@Import({ ApplicationConfig.class, FeignAutoConfiguration.class })
@EnableFeignClients
public @interface EnableTWTFeignClients
{
    String[] value() default {};

    String[] basePackages() default { "com.twelvet" };

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}