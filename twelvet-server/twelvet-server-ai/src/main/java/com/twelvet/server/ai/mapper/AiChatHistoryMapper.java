package com.twelvet.server.ai.mapper;

import com.twelvet.api.ai.domain.AiChatHistory;

import java.util.List;

/**
 * AI聊天记录Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
public interface AiChatHistoryMapper {

	/**
	 * 查询AI聊天记录
	 * @param chatHistoryId AI聊天记录主键
	 * @return AI聊天记录
	 */
	public AiChatHistory selectAiChatHistoryByChatHistoryId(Long chatHistoryId);

	/**
	 * 查询AI聊天记录列表
	 * @param aiChatHistory AI聊天记录
	 * @return AI聊天记录集合
	 */
	public List<AiChatHistory> selectAiChatHistoryList(AiChatHistory aiChatHistory);

	/**
	 * 新增AI聊天记录
	 * @param aiChatHistory AI聊天记录
	 * @return 结果
	 */
	public int insertAiChatHistory(AiChatHistory aiChatHistory);

	/**
	 * 修改AI聊天记录
	 * @param aiChatHistory AI聊天记录
	 * @return 结果
	 */
	public int updateAiChatHistory(AiChatHistory aiChatHistory);

	/**
	 * 删除AI聊天记录
	 * @param chatHistoryId AI聊天记录主键
	 * @return 结果
	 */
	public int deleteAiChatHistoryByChatHistoryId(Long chatHistoryId);

	/**
	 * 批量删除AI聊天记录
	 * @param chatHistoryIds 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteAiChatHistoryByChatHistoryIds(Long[] chatHistoryIds);

}