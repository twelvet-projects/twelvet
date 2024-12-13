package com.twelvet.server.ai.service;

import com.twelvet.api.ai.domain.AiChatHistoryContent;

import java.util.List;

/**
 * AI会话内容详情Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
public interface IAiChatHistoryContentService {

	/**
	 * 查询AI会话内容详情
	 * @param chatHistoryContentId AI会话内容详情主键
	 * @return AI会话内容详情
	 */
	public AiChatHistoryContent selectAiChatHistoryContentByChatHistoryContentId(Long chatHistoryContentId);

	/**
	 * 查询AI会话内容详情列表
	 * @param aiChatHistoryContent AI会话内容详情
	 * @return AI会话内容详情集合
	 */
	public List<AiChatHistoryContent> selectAiChatHistoryContentList(AiChatHistoryContent aiChatHistoryContent);

}
