package com.twelvet.framework.swagger.annotation;

import com.twelvet.framework.swagger.config.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.lang.annotation.*;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 开启 swagger
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
// @Import({ SwaggerAutoConfiguration.class })
public @interface EnableTwelveTSwagger2 {

}
