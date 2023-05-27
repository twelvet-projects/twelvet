package com.twelvet.framework.log.utils;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.twelvet.framework.log.vo.SysLogVO;
import com.twelvet.framework.utils.http.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 日志工具类
 */
public class SysLogUtils {

	public static SysLogVO getSysLog() {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
			.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		SysLogVO sysLogVO = new SysLogVO();
		sysLogVO.setRemoteAddr(IpUtils.getIpAddr());
		sysLogVO.setRequestUri(URLUtil.getPath(request.getRequestURI()));
		sysLogVO.setMethod(request.getMethod());
		sysLogVO.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
		sysLogVO.setParams(HttpUtil.toParams(request.getParameterMap()));
		return sysLogVO;
	}

	/**
	 * 获取用户名称
	 * @return username
	 */
	private static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return authentication.getName();
	}

	/**
	 * 获取spel 定义的参数值
	 * @param context 参数容器
	 * @param key key
	 * @param clazz 需要返回的类型
	 * @param <T> 返回泛型
	 * @return 参数值
	 */
	public static <T> T getValue(EvaluationContext context, String key, Class<T> clazz) {
		SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
		Expression expression = spelExpressionParser.parseExpression(key);
		return expression.getValue(context, clazz);
	}

	/**
	 * 获取参数容器
	 * @param arguments 方法的参数列表
	 * @param signatureMethod 被执行的方法体
	 * @return 装载参数的容器
	 */
	public static EvaluationContext getContext(Object[] arguments, Method signatureMethod) {
		String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(signatureMethod);
		EvaluationContext context = new StandardEvaluationContext();
		if (parameterNames == null) {
			return context;
		}
		for (int i = 0; i < arguments.length; i++) {
			context.setVariable(parameterNames[i], arguments[i]);
		}
		return context;
	}

}
