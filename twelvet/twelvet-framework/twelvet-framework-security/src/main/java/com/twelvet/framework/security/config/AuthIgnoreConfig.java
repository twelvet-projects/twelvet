package com.twelvet.framework.security.config;

import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.twelvet.framework.security.annotation.AuthIgnore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 忽略服务间的认证
 */
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class AuthIgnoreConfig implements InitializingBean {

	private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

	private List<String> urls = new ArrayList<>();

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	/**
	 * 重写bean注入后
	 */
	@Override
	public void afterPropertiesSet() {
		RequestMappingHandlerMapping mapping = SpringUtil.getBean("requestMappingHandlerMapping");
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

		// 遍历所有mapping
		map.keySet().forEach(mappingInfo -> {

			HandlerMethod handlerMethod = map.get(mappingInfo);
			// 获取方法上边的注解 替代path variable 为 *
			AuthIgnore method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), AuthIgnore.class);
			Optional.ofNullable(method)
				.ifPresent(authIgnore -> Objects.requireNonNull(mappingInfo.getPathPatternsCondition())
					.getPatternValues()
					.forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));

			// 获取类上边的注解, 替代path variable 为 *
			AuthIgnore controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), AuthIgnore.class);
			Optional.ofNullable(controller)
				.ifPresent(inner -> Objects.requireNonNull(mappingInfo.getPathPatternsCondition())
					.getPatternValues()
					.forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
		});
	}

}
