package com.twelvet.server.ai.mq;

import org.springframework.messaging.MessageChannel;

/**
 * RAG MQ通道
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-01-04
 */
public interface RAGChannel {

	/**
	 * 添加rag文档
	 */
	String ADD_RAG_DOC = "addRAGDocChannel-out-0";

}
