package com.twelvet.framework.swagger.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: Swagger实体
 */
@Component
@ConfigurationProperties("swagger")
public class SwaggerProperties {

	/**
	 * 是否开启swagger
	 */
	private Boolean enabled = true;

	/**
	 * swagger会解析的包路径
	 **/
	private String basePackage = "";

	/**
	 * 描述
	 **/
	private String description = "";

	/**
	 * 版本
	 **/
	private String version = "";

	/**
	 * swagger会解析的url规则
	 **/
	private List<String> basePath = new ArrayList<>();

	/**
	 * 在basePath基础上需要排除的url规则
	 **/
	private List<String> excludePath = new ArrayList<>();

	/**
	 * 需要排除的服务
	 */
	private List<String> ignoreProviders = new ArrayList<>();

	/**
	 * 标题
	 **/
	private String title = "";

	/**
	 * 网关
	 */
	private String gateway;

	/**
	 * 获取token
	 */
	private String tokenUrl;

	/**
	 * 作用域
	 */
	private String scope;

	/**
	 * 服务转发配置
	 */
	private Map<String, String> services;

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public List<String> getBasePath() {
		return basePath;
	}

	public void setBasePath(List<String> basePath) {
		this.basePath = basePath;
	}

	public List<String> getExcludePath() {
		return excludePath;
	}

	public void setExcludePath(List<String> excludePath) {
		this.excludePath = excludePath;
	}

	public List<String> getIgnoreProviders() {
		return ignoreProviders;
	}

	public void setIgnoreProviders(List<String> ignoreProviders) {
		this.ignoreProviders = ignoreProviders;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Map<String, String> getServices() {
		return services;
	}

	public void setServices(Map<String, String> services) {
		this.services = services;
	}

}
