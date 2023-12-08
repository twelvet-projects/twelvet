package com.twelvet.framework.openfeign.sentinel;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.twelvet.framework.openfeign.sentinel.ext.TwSentinelFeign;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * sentinel 配置
 *
 * @author twelvet
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class SentinelAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "spring.cloud.openfeign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return TwSentinelFeign.builder();
	}

}
