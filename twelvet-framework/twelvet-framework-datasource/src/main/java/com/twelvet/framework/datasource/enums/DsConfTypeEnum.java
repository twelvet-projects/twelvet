package com.twelvet.framework.datasource.enums;

/**
 * 数据源配置类型
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
public enum DsConfTypeEnum {

	/**
	 * 主机链接
	 */
	HOST("0", "主机链接"),

	/**
	 * JDBC链接
	 */
	JDBC("1", "JDBC链接");

	private final String type;

	private final String description;

	DsConfTypeEnum(String type, String description) {
		this.type = type;
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

}
