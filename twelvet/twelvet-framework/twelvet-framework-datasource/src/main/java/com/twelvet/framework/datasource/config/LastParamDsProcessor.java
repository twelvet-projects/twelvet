package com.twelvet.framework.datasource.config;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 参数数据源解析 @DS("#last)
 */
public class LastParamDsProcessor extends DsProcessor {

	private static final String LAST_PREFIX = "#last";

	/**
	 * 抽象匹配条件 匹配才会走当前执行器否则走下一级执行器
	 * @param key DS注解里的内容
	 * @return 是否匹配
	 */
	@Override
	public boolean matches(String key) {
		if (key.startsWith(LAST_PREFIX)) {
			// https://github.com/baomidou/dynamic-datasource-spring-boot-starter/issues/213
			DynamicDataSourceContextHolder.clear();
			return true;
		}
		return false;
	}

	/**
	 * 抽象最终决定数据源
	 * @param invocation 方法执行信息
	 * @param key DS注解里的内容
	 * @return 数据源名称
	 */
	@Override
	public String doDetermineDatasource(MethodInvocation invocation, String key) {
		Object[] arguments = invocation.getArguments();
		return String.valueOf(arguments[arguments.length - 1]);
	}

}
