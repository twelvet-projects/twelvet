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

	MessageChannel DOC_INPUT();

	MessageChannel DOC_OUTPUT();

}
