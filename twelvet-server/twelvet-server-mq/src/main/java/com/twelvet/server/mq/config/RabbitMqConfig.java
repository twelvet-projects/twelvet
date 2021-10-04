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
     * 系统登录日志队列
     *
     * @return Queue
     */
    @Bean
    public Queue loginLogQueue() {
        return new Queue(RabbitMQConstants.QUEUE_LOG_LOGIN);
    }

    /**
     * 系统操作日志队列
     *
     * @return Queue
     */
    @Bean
    public Queue operationLogQueue() {
        return new Queue(RabbitMQConstants.QUEUE_LOG_OPERATION);
    }

}
