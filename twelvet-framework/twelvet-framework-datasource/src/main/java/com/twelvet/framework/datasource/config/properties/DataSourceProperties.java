package com.twelvet.framework.datasource.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 数据源配置
 */
@ConfigurationProperties("spring.datasource")
public class DataSourceProperties {

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * jdbcurl
	 */
	private String url;

	/**
	 * 驱动类型
	 */
	private String driverClassName;

	/**
	 * 查询数据源的SQL
	 */
	private String queryDsSql = "select * from gen_datasource_conf where del_flag = 0";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getQueryDsSql() {
		return queryDsSql;
	}

	public void setQueryDsSql(String queryDsSql) {
		this.queryDsSql = queryDsSql;
	}

}
