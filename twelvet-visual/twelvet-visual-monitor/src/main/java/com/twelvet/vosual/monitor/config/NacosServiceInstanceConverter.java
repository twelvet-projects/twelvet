package com.twelvet.vosual.monitor.config;

import de.codecentric.boot.admin.server.cloud.discovery.DefaultServiceInstanceConverter;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 针对 nacos 2.x 服务注册处理
 */
@Configuration(proxyBeanMethods = false)
public class NacosServiceInstanceConverter extends DefaultServiceInstanceConverter {

	/**
	 * 重写方法
	 * @param instance ServiceInstance
	 * @return Map<String, String>
	 */
	@Override
	protected Map<String, String> getMetadata(ServiceInstance instance) {
		return (instance.getMetadata() != null) ? instance.getMetadata()
			.entrySet()
			.stream()
			.filter((e) -> e.getKey() != null && e.getValue() != null)
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)) : emptyMap();
	}

}
