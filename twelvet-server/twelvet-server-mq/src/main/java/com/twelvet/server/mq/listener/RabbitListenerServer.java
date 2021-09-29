package com.twelvet.server.mq.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twelvet.api.mq.constant.RabbitMQConstants;
import com.twelvet.api.mq.domain.MaillMq;
import com.twelvet.framework.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author twelvet
 * <p>
 * 监听消息
 */
@Component
public class RabbitListenerServer {

    private final static Logger log = LoggerFactory.getLogger(RabbitListenerServer.class);

    /**
     * 监听邮件消息
     *
     * @param message Message
     */
    @RabbitListener(queues = {
            RabbitMQConstants.QUEUE_MAIL
    })
    public void getMailMessage(Message message) {
        log.info("========监听到的消息：" + message);
        byte[] body = message.getBody();
        MaillMq maillMq = JacksonUtils.readValue(body, MaillMq.class);
        log.info("========监听到的消息body：" + maillMq.getToMail());
        log.info("========监message.getMessageProperties==：" + message.getMessageProperties());

    }

}
