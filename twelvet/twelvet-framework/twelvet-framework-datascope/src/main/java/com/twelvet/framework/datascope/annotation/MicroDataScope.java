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
	 * 管理部门ID字段写法
	 */
	String deptIdField() default "dept_id";

	/**
	 * 管理用户ID字段写法
	 */
	String userIdField() default "user_id";

}
