package com.twelvet.framework.datasource.config;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.core.Ordered;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 清空上文的DS 设置避免污染当前线程
 */
public class ClearTtlDataSourceFilter extends GenericFilterBean implements Ordered {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		DynamicDataSourceContextHolder.clear();
		filterChain.doFilter(servletRequest, servletResponse);
		DynamicDataSourceContextHolder.clear();
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
