package com.twelvet.server.mq.config;

import com.twelvet.api.mq.constant.RabbitMQExchangeConstants;
import com.twelvet.api.mq.constant.RabbitMQRoutingKeyConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
        return new Queue(
                RabbitMQRoutingKeyConstants.QUEUE_LOG_LOGIN,
                // 是否持久化
                true,
                // 是否排他
                false,
                // 是否自动删除
                false
        );
    }

    /**
     * 配置系统交换机
     * @return DirectExchange
     */
    @Bean
    public DirectExchange sysLoginLogDirectExchange() {
        return new DirectExchange(
                // 交换机名称
                RabbitMQExchangeConstants.DIRECT_LOG_LOGIN,
                // 是否持久化
                true,
                // 是否自动删除
                false
        );
    }

    /**
     * 绑定默认交换机
     *
     * @return Binding
     */
    @Bean
    public Binding bindingLoginLogExchange() {
        return BindingBuilder
                .bind(loginLogQueue())
                .to(sysLoginLogDirectExchange())
                .with(RabbitMQRoutingKeyConstants.QUEUE_LOG_LOGIN);
    }

    /**
     * 系统操作日志队列
     *
     * @return Queue
     */
    @Bean
    public Queue operationLogQueue() {
        return new Queue(
                RabbitMQRoutingKeyConstants.QUEUE_LOG_OPERATION,
                true,
                false,
                false
        );
    }

}
