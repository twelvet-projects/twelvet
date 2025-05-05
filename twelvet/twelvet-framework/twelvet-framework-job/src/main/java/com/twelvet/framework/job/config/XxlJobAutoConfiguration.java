package com.twelvet.framework.job.config;

import com.twelvet.framework.job.config.properties.XxlExecutorProperties;
import com.twelvet.framework.job.config.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * xxl-job自动装配
 *
 * @author twelvet
 * @date 2025/05/04
 */
@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobAutoConfiguration {

	/**
	 * 配置xxl-job 执行器，提供自动发现 xxl-job-admin 能力
	 * @param xxlJobProperties xxl 配置
	 * @param environment 环境变量
	 * @return XxlJobSpringExecutor
	 */
	@Bean
	public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties xxlJobProperties, Environment environment) {
		XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
		// 注册地址
		xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());

		XxlExecutorProperties executor = xxlJobProperties.getExecutor();
		// 应用名默认为服务名
		String appName = executor.getAppname();
		xxlJobSpringExecutor.setAppname(appName);
		xxlJobSpringExecutor.setAddress(executor.getAddress());
		xxlJobSpringExecutor.setIp(executor.getIp());
		xxlJobSpringExecutor.setPort(executor.getPort());
		xxlJobSpringExecutor.setAccessToken(executor.getAccessToken());
		xxlJobSpringExecutor.setLogPath(executor.getLogPath());
		xxlJobSpringExecutor.setLogRetentionDays(executor.getLogRetentionDays());

		return xxlJobSpringExecutor;
	}

}