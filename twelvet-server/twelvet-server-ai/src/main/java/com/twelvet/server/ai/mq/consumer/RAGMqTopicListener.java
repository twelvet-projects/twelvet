package com.twelvet.server.ai.mq.consumer;

import com.twelvet.api.ai.domain.dto.AiDocDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * RAG消息监听
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-01-04
 */
@Component
public class RAGMqTopicListener {

	private final static Logger log = LoggerFactory.getLogger(RAGMqTopicListener.class);

	@Bean
	public Consumer<String> addRAGDocChannel() {
		return message -> {
			log.info("收到消息: {}", message);
		};
	}

}
