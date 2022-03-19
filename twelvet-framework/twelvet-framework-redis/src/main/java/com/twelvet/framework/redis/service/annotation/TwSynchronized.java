package com.twelvet.framework.redis.service.annotation;


import java.lang.annotation.*;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 分布式可重入锁
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TwSynchronized {

    /**
     * 唯一锁名称
     */
    String value();

}
