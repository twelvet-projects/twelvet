package com.twelvet.framework.datascope.annotation;

import java.lang.annotation.*;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 分布式数据权限
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MicroDataScope {

	/**
	 * 部门的别名
	 */
	String deptAlias() default "";

	/**
	 * 用户的别名
	 */
	String userAlias() default "";

}
