package com.twelvet.server.ai.mq.consumer;

import com.twelvet.api.ai.domain.dto.AiDocDTO;
import com.twelvet.server.ai.service.IAiDocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

	private final IAiDocService iAiDocService;

	public RAGMqTopicListener(IAiDocService iAiDocService) {
		this.iAiDocService = iAiDocService;
	}

	/**
	 * 处理添加RAG文档消息
	 * @return Consumer<Message < AiDocDTO>>
	 */
	@Bean
	public Consumer<Message<AiDocDTO>> addRAGDocChannel() {
		return message -> {
			log.info("处理添加RAG文档消息: {}", message);
			iAiDocService.insertAiDoc(message.getPayload());
		};
	}

}
