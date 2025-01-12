package com.twelvet.server.ai.mq.consumer;

import com.twelvet.server.ai.mq.consumer.domain.dto.AiDocMqDTO;
import com.twelvet.server.ai.mq.consumer.service.RAGMqTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

/**
 * RAG消息监听
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-01-04
 */
@Configuration
public class RAGMqTopicListener {

	private final static Logger log = LoggerFactory.getLogger(RAGMqTopicListener.class);

	private final RAGMqTopicService ragMqTopicService;

	public RAGMqTopicListener(RAGMqTopicService ragMqTopicService) {
		this.ragMqTopicService = ragMqTopicService;
	}

	/**
	 * 处理添加RAG文档消息
	 * @return Consumer<Message < AiDocDTO>>
	 */
	@Bean
	public Consumer<Message<AiDocMqDTO>> addRAGDocChannel() {
		return message -> {
			log.info("处理添加RAG文档消息: {}", message);
			long startTime = System.nanoTime();
			ragMqTopicService.addRAGDocChannel(message);
			long endTime = System.nanoTime(); // 记录结束时间
			long duration = endTime - startTime; // 计算持续时间
			System.out.println("运行时间：" + duration / 1000000);
		};
	}

}
