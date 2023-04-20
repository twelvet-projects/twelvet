package com.twelvet.framework.core.application.page;

import com.twelvet.framework.utils.http.ServletUtils;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 表格数据处理
 */
public class TableSupport {

	/**
	 * 当前记录起始索引
	 */
	public static final String CURRENT = "current";

	/**
	 * 每页显示记录数
	 */
	public static final String PAGE_SIZE = "pageSize";

	/**
	 * 排序列
	 */
	public static final String ORDER_BY_COLUMN = "orderByColumn";

	/**
	 * 排序的方向 "desc" 或者 "asc".
	 */
	public static final String IS_ASC = "isAsc";

	/**
	 * 、封装分页对象
	 * @return 分页数据
	 */
	public static PageDomain getPageDomain() {
		PageDomain pageDomain = new PageDomain();
		pageDomain.setCurrent(ServletUtils.getParameterToInt(CURRENT));
		pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
		pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
		pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
		return pageDomain;
	}

	/**
	 * 获取分页数据
	 * @return 数据数据
	 */
	public static PageDomain buildPageRequest() {
		return getPageDomain();
	}

}
