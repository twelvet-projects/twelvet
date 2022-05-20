
package com.twelvet.server.mq.service.impl;
/**
 * @author twelvet
 * <p>
 * 系统操作日志业务层实现
 */
import com.twelvet.api.mq.constant.MQTopicConstants;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.server.mq.service.SysOperationLogService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 发送系统操作日志MQ
     *
     * @param sysOperationLog SysLoginInfo
     */
    @Override
    public void sendSysOperationLog(SysOperationLog sysOperationLog) {
        rocketMQTemplate.convertAndSend(
                MQTopicConstants.QUEUE_LOG_OPERATION,
                JacksonUtils.toJson(sysOperationLog)
        );
    }
}

