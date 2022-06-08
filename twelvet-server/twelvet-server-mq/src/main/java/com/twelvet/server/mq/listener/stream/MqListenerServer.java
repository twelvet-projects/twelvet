
package com.twelvet.server.mq.listener.stream;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.api.system.feign.RemoteLogService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 监听系统操作日志消息
 */
@Component
public class MqListenerServer {

	private final static Logger log = LoggerFactory.getLogger(MqListenerServer.class);

	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 监听系统操作日志消息
	 * @return Function
	 */
	public Function<Flux<Message<SysOperationLog>>, Mono<Void>> operationLog() {
		return flux -> flux.map(sysOperationLogMessage -> {
			{
				MessageHeaders headers = sysOperationLogMessage.getHeaders();
				SysOperationLog payload = sysOperationLogMessage.getPayload();
				log.error("收到操作消息：{}", payload.getOperId());

				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		}).then();
	}

}
