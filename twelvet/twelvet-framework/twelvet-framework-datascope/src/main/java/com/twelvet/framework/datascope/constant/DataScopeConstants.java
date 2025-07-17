package com.twelvet.framework.datascope.constant;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 权限表示常量
 */
public interface DataScopeConstants {

	/**
	 * 全部数据权限
	 */
	String DATA_SCOPE_ALL = "1";

	/**
	 * 自定数据权限
	 */
	String DATA_SCOPE_CUSTOM = "2";

	/**
	 * 部门数据权限
	 */
	String DATA_SCOPE_DEPT = "3";

	/**
	 * 部门及以下数据权限
	 */
	String DATA_SCOPE_DEPT_AND_CHILD = "4";

	/**
	 * 仅本人数据权限
	 */
	String DATA_SCOPE_SELF = "5";

	/**
	 * 数据权限过滤关键字
	 */
	String DATA_SCOPE = "dataScope";

}
