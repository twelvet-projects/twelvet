
package com.twelvet.server.mq.service.impl;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.server.mq.service.SysLoginLogService;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 邮件业务层实现
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    private final static Logger log = LoggerFactory.getLogger(SysLoginLogServiceImpl.class);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private StreamBridge streamBridge;

    /**
     * 发送系统登录日志MQ
     * 同步发送：syncSend，可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知，影响响应时间
     * 异步发送：asyncSend，通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应
     * 不影响响应时间的同时需要保证消息不丢失的情况下需要自行处理异步回调，重试机制
     * 单向发送：convertAndSend，主要用在不特别关心发送结果的场景，例如日志发送，可能会存在消息丢失
     * https://docs.spring.io/spring-cloud-stream/docs/3.1.0/reference/html/spring-cloud-stream.html#_binding_and_binding_names
     *
     * @param sysLoginInfo SysLoginInfo
     */
    @Override
    public void sendSysLoginLog(SysLoginInfo sysLoginInfo) {
        sysLoginInfo.setInfoId(1L);
        Message<SysLoginInfo> message = MessageBuilder.withPayload(sysLoginInfo)
                .setHeader(MessageConst.PROPERTY_TAGS, "system-login")
                .setHeader(MessageConst.PROPERTY_KEYS, "test")
                .build();
        streamBridge.send("loginLog-out-0", message);
        /*rocketMQTemplate.asyncSend(
                MQTopicConstants.QUEUE_LOG_LOGIN,
                message,
                new SendCallback() {

                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("发送成功：");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        log.error("发送失败：", throwable);
                    }
                });*/

    }
}

