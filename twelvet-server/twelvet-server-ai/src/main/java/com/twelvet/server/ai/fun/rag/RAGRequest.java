package com.twelvet.server.ai.fun.rag;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * RAG知识库请求
 *
 * @author yuanci.ytb
 * @since 2025/07/28 11:29
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RAGRequest(
		@JsonProperty(required = true, value = "content") @JsonPropertyDescription("需要对知识库进行查询的提问内容") String content) {

}
