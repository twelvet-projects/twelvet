package com.twelvet.server.mq.service.impl;

import com.twelvet.api.mq.constant.RabbitMQConstants;
import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.server.mq.service.SysLoginLogService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author twelvet
 * <p>
 * 邮件业务层实现
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送系统登录日志MQ
     *
     * @param sysLoginInfo SysLoginInfo
     */
    @Override
    public void sendSysLoginLog(SysLoginInfo sysLoginInfo) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.QUEUE_LOG_LOGIN, JacksonUtils.toJson(sysLoginInfo));
    }
}
