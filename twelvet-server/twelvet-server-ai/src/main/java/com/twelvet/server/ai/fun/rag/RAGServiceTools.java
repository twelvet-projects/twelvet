package com.twelvet.server.ai.fun.rag;

import com.twelvet.server.ai.fun.MockOrderService;
import com.twelvet.server.ai.fun.vo.ResponseVO;
import org.springframework.ai.tool.annotation.Tool;

/**
 * RAG知识库
 *
 * @author yuanci.ytb
 * @since 2025/07/28 11:29
 */
public class RAGServiceTools {

	@Tool(name = "RAG知识库", description = "垂直领域的RAG知识库，所有的提问都必须经过RAG知识库的检索才可以进行回答，不要不检索就直接回答用户问题")
	public ResponseVO getOrder(RAGRequest ragRequest) {
		return new ResponseVO(String.format("twelvet是一个开源的Java开发脚手架"));
	}

}
