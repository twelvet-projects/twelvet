package com.twelvet.server.mq.listener;

import com.twelvet.api.mq.constant.MQRoutingKeyConstants;
import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.api.system.feign.RemoteLogService;
import com.twelvet.framework.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author twelvet
 * <p>
 * 监听消息
 */
@Component
public class RabbitListenerServer {

    private final static Logger log = LoggerFactory.getLogger(RabbitListenerServer.class);

    @Autowired
    private RemoteLogService remoteLogService;

    /**
     * 监听系统登录日志消息
     *
     * @param message Message
     */
    @RabbitListener(queues = {
            MQRoutingKeyConstants.QUEUE_LOG_LOGIN
    })
    public void insertLoginLogMessage(Message message) {
        log.info("收到系统登录MQ：{}", message);
        byte[] body = message.getBody();
        SysLoginInfo sysLoginInfo = JacksonUtils.readValue(body, SysLoginInfo.class);
        remoteLogService.saveLoginInfo(sysLoginInfo);
    }

    /**
     * 监听系统操作日志消息
     *
     * @param message Message
     */
    @RabbitListener(queues = {
            MQRoutingKeyConstants.QUEUE_LOG_OPERATION
    })
    public void insertOperationLogMessage(Message message) {
        log.info("收到系统操作MQ：{}", message);
        byte[] body = message.getBody();
        SysOperationLog sysOperationLog = JacksonUtils.readValue(body, SysOperationLog.class);
        remoteLogService.saveLog(sysOperationLog);
    }

}
