package com.twelvet.framework.log.annotation;

import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.log.enums.OperatorType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 自定义操作日志记录注解
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

	/**
	 * 模块名称
	 */
	String service() default "Unknown Service";

	/**
	 * 功能
	 */
	BusinessType businessType() default BusinessType.OTHER;

	/**
	 * 操作人类别
	 */
	OperatorType operatorType() default OperatorType.MANAGE;

	/**
	 * 是否保存请求的参数
	 */
	boolean isSaveRequestData() default true;

}
