package com.twelvet.framework.websocket.distribute;

import com.twelvet.framework.redis.service.RedisUtils;
import com.twelvet.framework.websocket.constants.WebSocketConstants;
import com.twelvet.framework.websocket.domain.vo.MessageVO;

/**
 * 基于 Redis 的消息分发器
 * <p>
 * 在集群环境下，通过 Redis 的发布/订阅机制，将消息发布到指定频道， 由所有订阅该频道的服务实例进行消费和处理，从而实现跨服务的消息分发。
 * </p>
 */
public class RedisMessageDistributor implements MessageDistributor {

    /**
     * 将消息发布到 Redis 频道，发布/订阅机制。
     *
     * @param messageVO 待发送的消息对象，包含消息内容和目标会话信息。
     */
    @Override
    public void distribute(MessageVO messageVO) {
        RedisUtils.setCacheObject(WebSocketConstants.CHANNEL, messageVO);
    }

}
