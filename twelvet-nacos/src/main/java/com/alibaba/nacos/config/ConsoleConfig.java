package com.alibaba.nacos.config;

import com.alibaba.nacos.core.code.ControllerMethodsCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import jakarta.annotation.PostConstruct;
import java.time.ZoneId;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 、Console config.
 */
@Component
@EnableScheduling
@PropertySource("/application.properties")
public class ConsoleConfig {

	@Autowired
	private ControllerMethodsCache methodsCache;

	/**
	 * Init.
	 */
	@PostConstruct
	public void init() {
		methodsCache.initClassMethod("com.alibaba.nacos.core.controller");
		methodsCache.initClassMethod("com.alibaba.nacos.naming.controllers");
		methodsCache.initClassMethod("com.alibaba.nacos.config.server.controller");
		methodsCache.initClassMethod("com.alibaba.nacos.controller");
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.setMaxAge(18000L);
		config.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
		return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(ZoneId.systemDefault().toString());
	}

}
