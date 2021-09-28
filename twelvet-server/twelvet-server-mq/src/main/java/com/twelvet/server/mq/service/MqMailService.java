package com.twelvet.server.mq.service;

import com.twelvet.api.mq.domain.MaillMq;

/**
 * @author twelvet
 * <p>
 * 邮件业务层
 */
public interface MqMailService {

    /**
     * 发送MQ消息
     */
    void send(MaillMq maillMq);

}
