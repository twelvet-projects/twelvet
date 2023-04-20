package com.twelvet.framework.utils.function;

import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 函数式接口
 */
@FunctionalInterface
public interface CheckedConsumer<T> extends Serializable {

	/**
	 * Run the Consumer
	 * @param var1 T
	 * @throws Throwable UncheckedException
	 */

	@Nullable
	void accept(@Nullable T var1) throws Throwable;

}
