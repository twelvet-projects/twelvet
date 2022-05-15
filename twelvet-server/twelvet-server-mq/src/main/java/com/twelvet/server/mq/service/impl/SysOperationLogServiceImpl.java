/*
package com.twelvet.server.mq.service.impl;

import com.twelvet.api.mq.constant.MQRoutingKeyConstants;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.server.mq.service.SysOperationLogService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

*/
/**
 * @author twelvet
 * <p>
 * 系统操作日志业务层实现
 *//*

@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    */
/**
     * 发送系统操作日志MQ
     *
     * @param sysOperationLog SysLoginInfo
     *//*

    @Override
    public void sendSysOperationLog(SysOperationLog sysOperationLog) {
        rabbitTemplate.convertAndSend(
                MQRoutingKeyConstants.QUEUE_LOG_OPERATION,
                JacksonUtils.toJson(sysOperationLog)
        );
    }
}
*/
