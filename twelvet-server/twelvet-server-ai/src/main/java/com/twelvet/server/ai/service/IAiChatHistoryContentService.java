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

	/**
	 * 新增AI会话内容详情
	 * @param aiChatHistoryContent AI会话内容详情
	 * @return 结果
	 */
	public int insertAiChatHistoryContent(AiChatHistoryContent aiChatHistoryContent);

	/**
	 * 修改AI会话内容详情
	 * @param aiChatHistoryContent AI会话内容详情
	 * @return 结果
	 */
	public int updateAiChatHistoryContent(AiChatHistoryContent aiChatHistoryContent);

	/**
	 * 批量删除AI会话内容详情
	 * @param chatHistoryContentIds 需要删除的AI会话内容详情主键集合
	 * @return 结果
	 */
	public int deleteAiChatHistoryContentByChatHistoryContentIds(Long[] chatHistoryContentIds);

	/**
	 * 删除AI会话内容详情信息
	 * @param chatHistoryContentId AI会话内容详情主键
	 * @return 结果
	 */
	public int deleteAiChatHistoryContentByChatHistoryContentId(Long chatHistoryContentId);

}
