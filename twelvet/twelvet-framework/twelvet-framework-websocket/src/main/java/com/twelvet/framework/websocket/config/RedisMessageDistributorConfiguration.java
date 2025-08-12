package com.twelvet.framework.websocket.config;

import com.twelvet.framework.websocket.config.properties.WebSocketProperties;
import com.twelvet.framework.websocket.constants.MessageDistributorTypeConstants;
import com.twelvet.framework.websocket.constants.WebSocketConstants;
import com.twelvet.framework.websocket.distribute.MessageDistributor;
import com.twelvet.framework.websocket.distribute.RedisMessageDistributor;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * 基于 Redis Pub/Sub 的消息分发器配置
 * <p>
 * 当 WebSocket 配置为使用 Redis 进行消息分发时，此配置类生效。 它提供了基于 Redis 的 {@link MessageDistributor}
 * 实现，适用于集群环境。
 * </p>
 *
 * @author hccake
 */
@ConditionalOnClass(StringRedisTemplate.class)
@ConditionalOnProperty(prefix = WebSocketProperties.PREFIX, name = "message-distributor",
		havingValue = MessageDistributorTypeConstants.REDIS)
@Configuration(proxyBeanMethods = false)
public class RedisMessageDistributorConfiguration {

	/**
	 * 创建一个基于 Redis 的消息分发器。
	 * @param stringRedisTemplate Spring Data Redis 提供的 Redis 操作模板。
	 * @return 返回一个 {@link RedisMessageDistributor} 实例。
	 */
	@Bean
	@ConditionalOnMissingBean(MessageDistributor.class)
	public RedisMessageDistributor messageDistributor(StringRedisTemplate stringRedisTemplate) {
		return new RedisMessageDistributor(stringRedisTemplate);
	}

	/**
	 * 创建 Redis 消息监听器容器，用于管理消息监听器。
	 * @param connectionFactory Redis 连接工厂。
	 * @return 返回一个 {@link RedisMessageListenerContainer} 实例。
	 */
	@Bean
	@ConditionalOnBean(RedisMessageDistributor.class)
	@ConditionalOnMissingBean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		return container;
	}

	/**
	 * Redis 消息监听器注册配置
	 * <p>
	 * 在 Spring 初始化后，将 {@link RedisMessageDistributor} 注册到 Redis 消息监听器容器中。
	 * </p>
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingBean(MessageDistributor.class)
	static class RedisMessageListenerRegisterConfiguration {

		private final RedisMessageListenerContainer redisMessageListenerContainer;

		private final RedisMessageDistributor redisWebsocketMessageListener;

		RedisMessageListenerRegisterConfiguration(RedisMessageListenerContainer redisMessageListenerContainer,
				RedisMessageDistributor redisWebsocketMessageListener) {
			this.redisMessageListenerContainer = redisMessageListenerContainer;
			this.redisWebsocketMessageListener = redisWebsocketMessageListener;
		}

		/**
		 * 将 WebSocket 消息监听器添加到 Redis 监听容器中，监听指定频道的消息。
		 */
		@PostConstruct
		public void addMessageListener() {
			redisMessageListenerContainer.addMessageListener(redisWebsocketMessageListener,
					new PatternTopic(WebSocketConstants.CHANNEL));
		}

	}

}
