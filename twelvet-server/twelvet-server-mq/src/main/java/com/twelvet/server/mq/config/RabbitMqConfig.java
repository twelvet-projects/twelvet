package com.twelvet.server.mq.config;

import com.twelvet.api.mq.constant.RabbitMQConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author twelvet
 * <p>
 * rabbitmq队列配置
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 邮件队列
     *
     * @return Queue
     */
    @Bean
    public Queue mailQueue() {
        return new Queue(RabbitMQConstants.QUEUE_MAIL);
    }

}
