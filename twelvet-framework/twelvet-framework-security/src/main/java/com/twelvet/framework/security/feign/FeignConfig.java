package com.twelvet.framework.security.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: Feign 配置注册
 */
public class FeignConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new FeignRequestInterceptor();
	}

}
