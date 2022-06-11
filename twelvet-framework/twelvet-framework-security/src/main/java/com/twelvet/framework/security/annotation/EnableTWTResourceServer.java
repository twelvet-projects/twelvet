package com.twelvet.framework.security.annotation;

import com.twelvet.framework.security.config.TWTResourceServerAutoConfiguration;
import com.twelvet.framework.security.config.TWTResourceServerConfiguration;
import com.twelvet.framework.security.feign.FeignConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({
        TWTResourceServerAutoConfiguration.class,
        TWTResourceServerConfiguration.class,
        FeignConfig.class
})
public @interface EnableTWTResourceServer {

}