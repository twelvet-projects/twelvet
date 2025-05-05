package com.twelvet.framework.job.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * xxl-job配置
 *
 * @author twelvet
 * @date 2025/05/04
 */
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

	/**
	 * 调度中心配置
	 */
	@NestedConfigurationProperty
	private XxlAdminProperties admin;

	/**
	 * 执行器配置
	 */
	@NestedConfigurationProperty
	private XxlExecutorProperties executor;

	public XxlAdminProperties getAdmin() {
		return admin;
	}

	public void setAdmin(XxlAdminProperties admin) {
		this.admin = admin;
	}

	public XxlExecutorProperties getExecutor() {
		return executor;
	}

	public void setExecutor(XxlExecutorProperties executor) {
		this.executor = executor;
	}

}