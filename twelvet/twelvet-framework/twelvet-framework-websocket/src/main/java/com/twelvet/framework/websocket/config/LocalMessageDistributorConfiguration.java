package com.twelvet.framework.websocket.config;

import com.twelvet.framework.websocket.config.properties.WebSocketProperties;
import com.twelvet.framework.websocket.constants.MessageDistributorTypeConstants;
import com.twelvet.framework.websocket.distribute.LocalMessageDistributor;
import com.twelvet.framework.websocket.distribute.MessageDistributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 本地的消息分发器配置
 *
 * @author hccake
 */
@ConditionalOnProperty(prefix = WebSocketProperties.PREFIX, name = "message-distributor",
		havingValue = MessageDistributorTypeConstants.LOCAL, matchIfMissing = true)
@Configuration(proxyBeanMethods = false)
public class LocalMessageDistributorConfiguration {

	/**
	 * 配置本地消息分发器，使用本地内存实现，不支持集群环境。
	 * @return 返回一个 {@link LocalMessageDistributor} 实例，用于处理本地消息分发。
	 */
	@Bean
	@ConditionalOnMissingBean(MessageDistributor.class)
	public LocalMessageDistributor messageDistributor() {
		return new LocalMessageDistributor();
	}

}
