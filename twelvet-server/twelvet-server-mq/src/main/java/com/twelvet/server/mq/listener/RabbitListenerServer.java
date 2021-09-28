package com.twelvet.server.mq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
    @RabbitListener
    public void getMailMessage(Message message) {
        log.info("========监听到的消息：" + message);
        byte[] body = message.getBody();
        //ObjectMapper objectMapper = new ObjectMapper();
        //Info info = objectMapper.readValue(body, Info.class); //转化为对应的类
        String msg = new String(body);
        log.info("========监听到的消息body：" + msg);
        log.info("========监message.getMessageProperties==：" + message.getMessageProperties());
    }

}
