package com.twelvet.server.ai.constant;

/**
 * <p>
 * RAG相关常量
 * <p>
 *
 * @since 2024/12/12
 */
public interface RAGConstants {

	/**
	 * RAG系统提示词
	 */
	String RAG_SYSTEM_PROMPT = """
			Context information is below.
			---------------------
			{question_answer_context}
			---------------------
			Given the context and provided history information and not prior knowledge,
			reply to the user comment. If the answer is not in the context, inform
			the user that you can't answer the question.
			""";

}
