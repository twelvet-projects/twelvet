package com.twelvet.framework.core.annotation;

import com.twelvet.framework.swagger.annotation.EnableTwelveTSwagger2;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * @author twelvet
 * @Description: 自定义核心注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 开启Swagger
@EnableTwelveTSwagger2
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 开启线程异步执行
@EnableAsync
public @interface EnableTwelveTConfig {

}
