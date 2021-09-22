package com.twelvet.framework.utils.function;

import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 函数式接口
 */
@FunctionalInterface
public interface CheckedConsumer<T> extends Serializable {
    @Nullable
    void accept(@Nullable T var1) throws Throwable;
}