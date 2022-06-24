package com.twelvet.framework.security.config;

import cn.hutool.extra.spring.SpringUtil;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.framework.security.config.properties.IgnoreUrlsProperties;
import com.twelvet.framework.utils.$;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 忽略服务间的认证
 */
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class AuthIgnoreConfig implements InitializingBean {

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
			// 检测方法是否存在注解
			AuthIgnore method = handlerMethod.getMethod().getAnnotation(AuthIgnore.class);
			// 检测Controller是否在注解
			AuthIgnore controller = handlerMethod.getBeanType().getAnnotation(AuthIgnore.class);

			// 本方法或本Controller存在AuthIgnore注解将存进列表
			if ($.isNotEmpty(method) || $.isNotEmpty(controller)) {
				urls.addAll(mappingInfo.getPatternsCondition().getPatterns());
			}
		});
	}

}
