package com.twelvet.framework.websocket.distribute;

import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.websocket.constants.WebSocketConstants;
import com.twelvet.framework.websocket.domain.vo.MessageVO;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

/**
 * <p>
 * 基于Redis实现消息分发路由分发
 * </p>
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-08-11
 */
public class RedisMessageDistributor implements MessageDistributor, MessageListener, MessageSender {

	private final static Logger log = LoggerFactory.getLogger(RedisMessageDistributor.class);

	private final StringRedisTemplate stringRedisTemplate;

	public RedisMessageDistributor(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 将消息发布到 Redis 频道，发布/订阅机制。
	 * @param messageVO 待发送的消息对象，包含消息内容和目标会话信息。
	 */
	@Override
	public void distribute(MessageVO messageVO) {
		String json = JacksonUtils.toJson(messageVO);
		stringRedisTemplate.convertAndSend(WebSocketConstants.CHANNEL, json);
	}

	/**
	 * 监听Redis分发消息
	 * @param message Message
	 * @param pattern byte[]
	 */
	@Override
	public void onMessage(@NotNull Message message, @Nullable byte[] pattern) {
		log.info("收到redis分发websocket消息 {}", message);
		byte[] channelBytes = message.getChannel();
		RedisSerializer<String> stringSerializer = stringRedisTemplate.getStringSerializer();
		String channel = stringSerializer.deserialize(channelBytes);

		if (WebSocketConstants.CHANNEL.equals(channel)) {
			byte[] bodyBytes = message.getBody();
			String body = stringSerializer.deserialize(bodyBytes);
			MessageVO messageDO = JacksonUtils.readValue(body, MessageVO.class);
			assert messageDO != null;
			doSend(messageDO);
		}
	}

}
