package com.twelvet.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.twelvet.gateway.handler.SentinelFallbackHandler;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.TimeZone;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 网关限流配置
 */
@Configuration
public class GatewayConfig {

	/**
	 * 时区配置
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
		return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SentinelFallbackHandler sentinelGatewayExceptionHandler() {
		return new SentinelFallbackHandler();
	}

	@Order(-1)
	public GlobalFilter sentinelGatewayFilter() {
		return new SentinelGatewayFilter();
	}

}
