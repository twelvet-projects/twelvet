package com.twelvet.server.ai.mq.consumer.service;

import com.twelvet.server.ai.mq.consumer.domain.dto.AiDocMqDTO;
import org.springframework.messaging.Message;

/**
 * <p>
 * RAG消息处理服务
 * <p>
 *
 * @since 2025/1/10
 */
public interface RAGMqTopicService {

	/**
	 * 处理添加RAG文档消息
	 * @param message Message<AddRAGDocDTO>
	 */
	void addRAGDocChannel(Message<AiDocMqDTO> message);

}
