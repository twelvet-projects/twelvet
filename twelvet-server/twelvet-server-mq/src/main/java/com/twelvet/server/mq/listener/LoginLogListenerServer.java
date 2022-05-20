
package com.twelvet.server.mq.listener;

import com.twelvet.api.mq.constant.MQTopicConstants;
import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.feign.RemoteLogService;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author twelvet
 * <p>
 * 监听系统登录日志消息
 */
@RocketMQMessageListener(
        // 订阅组
        consumerGroup = "twelvet-test",
        // 主题
        topic = MQTopicConstants.QUEUE_LOG_LOGIN,
        // 消费类型(并发，不保证顺序)
        consumeMode = ConsumeMode.CONCURRENTLY
)
@Component
public class LoginLogListenerServer implements RocketMQListener<SysLoginInfo> {

    private final static Logger log = LoggerFactory.getLogger(LoginLogListenerServer.class);

    @Autowired
    private RemoteLogService remoteLogService;

    @Override
    public void onMessage(SysLoginInfo sysLoginInfo) {
        log.info("收到系统登录MQ：{}", sysLoginInfo);
        /*remoteLogService.saveLoginInfo(sysLoginInfo);*/
    }


/*    监听系统操作日志消息
    @RabbitListener(queues = {
            MQRoutingKeyConstants.QUEUE_LOG_OPERATION
    })
    public void insertOperationLogMessage(Message message) {
        log.info("收到系统操作MQ：{}", message);
        byte[] body = message.getBody();
        SysOperationLog sysOperationLog = JacksonUtils.readValue(body, SysOperationLog.class);
        remoteLogService.saveLog(sysOperationLog);
    }*/

}

