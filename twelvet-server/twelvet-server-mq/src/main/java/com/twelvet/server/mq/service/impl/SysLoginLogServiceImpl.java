
package com.twelvet.server.mq.service.impl;

import com.twelvet.api.mq.constant.MQGroupConstants;
import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.server.mq.service.SysLoginLogService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
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
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送系统登录日志MQ
     *
     * @param sysLoginInfo SysLoginInfo
     */
    @Override
    public void sendSysLoginLog(SysLoginInfo sysLoginInfo) {
        rocketMQTemplate.convertAndSend(
                // topic
                MQGroupConstants.QUEUE_LOG_LOGIN,
                sysLoginInfo
        );
    }
}

