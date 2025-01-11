package com.twelvet.server.ai.mq.consumer.service;

import com.twelvet.api.ai.domain.dto.AiDocDTO;
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
	 * @param message Message<AiDocDTO>
	 */
	void addRAGDocChannel(Message<AiDocDTO> message);

}
