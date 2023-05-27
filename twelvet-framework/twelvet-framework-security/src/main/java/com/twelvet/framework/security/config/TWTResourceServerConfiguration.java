package com.twelvet.framework.security.config;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 安全配置
 */
@EnableWebSecurity
public class TWTResourceServerConfiguration {

	@Autowired
	protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

	@Autowired
	private AuthIgnoreConfig permitAllUrl;

	@Autowired
	private TWTBearerTokenExtractor twtBearerTokenExtractor;

	@Autowired
	private OpaqueTokenIntrospector customOpaqueTokenIntrospector;

	/**
	 * 配置授权，开放部分url，自定义oauth处理
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
			.requestMatchers(ArrayUtil.toArray(permitAllUrl.getUrls(), String.class))
			.permitAll()
			.anyRequest()
			.authenticated())
			.oauth2ResourceServer(
					oauth2 -> oauth2.opaqueToken(token -> token.introspector(customOpaqueTokenIntrospector))
						.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
						.bearerTokenResolver(twtBearerTokenExtractor))
			.headers(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

}
