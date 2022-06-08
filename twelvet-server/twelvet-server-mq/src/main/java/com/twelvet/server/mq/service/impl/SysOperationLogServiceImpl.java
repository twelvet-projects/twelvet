
package com.twelvet.server.mq.service.impl;

/**
 * @author twelvet
 * <p>
 * 系统操作日志业务层实现
 */

import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.server.mq.service.SysOperationLogService;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {

	@Autowired
	private StreamBridge streamBridge;

	/**
	 * 发送系统操作日志MQ
	 * @param sysOperationLog SysLoginInfo
	 */
	@Override
	public void sendSysOperationLog(SysOperationLog sysOperationLog) {
		Message<SysOperationLog> message = MessageBuilder.withPayload(sysOperationLog)
				.setHeader(RocketMQHeaders.TAGS, "system-login").setHeader(RocketMQHeaders.KEYS, "test").build();
		sysOperationLog.setOperId(1L);
		streamBridge.send("loginLog-out-0", message);
	}

}
