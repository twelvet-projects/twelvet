package com.twelvet.server.mq.service.impl;

import com.twelvet.api.mq.constant.RabbitMQConstants;
import com.twelvet.api.mq.domain.MaillMq;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.server.mq.service.MqMailService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author twelvet
 * <p>
 * 邮件业务层实现
 */
@Service
public class MqMailServiceImpl implements MqMailService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送MQ消息
     */
    @Override
    public void send(MaillMq maillMq) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.QUEUE_MAIL, JacksonUtils.toJson(maillMq));

    }
}
