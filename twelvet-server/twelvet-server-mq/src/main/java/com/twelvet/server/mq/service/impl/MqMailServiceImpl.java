package com.twelvet.server.mq.service.impl;

import com.twelvet.api.mq.domain.MaillMq;
import com.twelvet.server.mq.service.MqMailService;
import org.springframework.stereotype.Service;

/**
 * @author twelvet
 * <p>
 * 邮件业务层实现
 */
@Service
public class MqMailServiceImpl implements MqMailService {

    /**
     * 发送MQ消息
     */
    @Override
    public void send(MaillMq maillMq) {



    }
}
