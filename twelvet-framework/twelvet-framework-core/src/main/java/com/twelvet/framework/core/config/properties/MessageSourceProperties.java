package com.twelvet.framework.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 国际化路径配置
 *
 * @author twelvet
 */
@ConfigurationProperties(prefix = "spring.messages")
public class MessageSourceProperties {

	/**
	 * 基础路径
	 */
	private String basename;

	/**
	 * 缓存持续时间
	 */
	private Long cacheDuration;

	public String getBasename() {
		return basename;
	}

	public void setBasename(String basename) {
		this.basename = basename;
	}

	public Long getCacheDuration() {
		return cacheDuration;
	}

	public void setCacheDuration(Long cacheDuration) {
		this.cacheDuration = cacheDuration;
	}

}
