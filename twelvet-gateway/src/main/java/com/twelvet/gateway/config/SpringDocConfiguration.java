package com.twelvet.gateway.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 聚合接口文档注册
 */
@Configuration(proxyBeanMethods = false)
public class SpringDocConfiguration {

	@Bean
	@Lazy(false)
	@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
	public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
									 SwaggerDocProperties swaggerProperties) {
		List<GroupedOpenApi> groups = new ArrayList<>();
		for (String value : swaggerProperties.getServices().values()) {
			swaggerUiConfigParameters.addGroup(value);
		}
		return groups;
	}

	@Component
	@ConfigurationProperties("swagger")
	public static class SwaggerDocProperties {

		private Map<String, String> services;

		/**
		 * 认证参数
		 */
		private SwaggerBasic basic = new SwaggerBasic();

		public Map<String, String> getServices() {
			return services;
		}

		public void setServices(Map<String, String> services) {
			this.services = services;
		}

		public SwaggerBasic getBasic() {
			return basic;
		}

		public void setBasic(SwaggerBasic basic) {
			this.basic = basic;
		}

		public class SwaggerBasic {

			/**
			 * 是否开启 basic 认证
			 */
			private Boolean enabled;

			/**
			 * 用户名
			 */
			private String username;

			/**
			 * 密码
			 */
			private String password;

			public Boolean getEnabled() {
				return enabled;
			}

			public void setEnabled(Boolean enabled) {
				this.enabled = enabled;
			}

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
		}

	}

}
