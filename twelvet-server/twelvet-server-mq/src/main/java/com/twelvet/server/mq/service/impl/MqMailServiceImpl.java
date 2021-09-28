package com.twelvet.server.mq.service.impl;

import com.twelvet.api.mq.domain.MaillMq;
import com.twelvet.server.mq.service.MqMailService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
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
    private AmqpTemplate amqpTemplate;

    /**
     * 发送MQ消息
     */
    @Override
    public void send(MaillMq maillMq) {

        amqpTemplate.send(
                "marketData.topic", "quotes.nasdaq.THING1",
                new Message("12.34".getBytes())
        );

    }
}
