package com.twelvet.framework.jdbc.web.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.twelvet.framework.core.application.page.PageDomain;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.core.application.page.TableSupport;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.framework.utils.sql.SqlUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 分页基础控制器
 */
public class PageUtils {

	/**
	 * 注入分页信息
	 */
	public static void startPage() {
		// 清除分页bug
		PageHelper.clearPage();
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer page = pageDomain.getCurrent();
		Integer pageSize = pageDomain.getPageSize();
		if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
			String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
			PageHelper.startPage(page, pageSize, orderBy);
		}
	}

	/**
	 * 响应请求分页数据
	 * @param list 数据列表
	 * @return 适应Json
	 */
	public static <T> TableDataInfo<T> getDataTable(List<T> list) {
		PageInfo<?> pageInfo = new PageInfo<>(list);
		return TableDataInfo.page(list, pageInfo.getTotal());
	}

}
