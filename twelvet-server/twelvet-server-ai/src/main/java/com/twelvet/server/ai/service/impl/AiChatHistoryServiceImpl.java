package com.twelvet.server.ai.service.impl;

import com.twelvet.api.ai.domain.AiChatHistory;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.server.ai.mapper.AiChatHistoryMapper;
import com.twelvet.server.ai.service.IAiChatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI聊天记录Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
@Service
public class AiChatHistoryServiceImpl implements IAiChatHistoryService {

	@Autowired
	private AiChatHistoryMapper aiChatHistoryMapper;

	/**
	 * 查询AI聊天记录
	 * @param chatHistoryId AI聊天记录主键
	 * @return AI聊天记录
	 */
	@Override
	public AiChatHistory selectAiChatHistoryByChatHistoryId(Long chatHistoryId) {
		return aiChatHistoryMapper.selectAiChatHistoryByChatHistoryId(chatHistoryId);
	}

	/**
	 * 查询AI聊天记录列表
	 * @param aiChatHistory AI聊天记录
	 * @return AI聊天记录
	 */
	@Override
	public List<AiChatHistory> selectAiChatHistoryList(AiChatHistory aiChatHistory) {
		return aiChatHistoryMapper.selectAiChatHistoryList(aiChatHistory);
	}

	/**
	 * 新增AI聊天记录
	 * @param aiChatHistory AI聊天记录
	 * @return 结果
	 */
	@Override
	public int insertAiChatHistory(AiChatHistory aiChatHistory) {
		LocalDateTime nowDate = LocalDateTime.now();
		aiChatHistory.setCreateTime(nowDate);
		aiChatHistory.setUpdateTime(nowDate);
		String loginUsername = SecurityUtils.getUsername();
		aiChatHistory.setCreateBy(loginUsername);
		aiChatHistory.setUpdateBy(loginUsername);
		return aiChatHistoryMapper.insertAiChatHistory(aiChatHistory);
	}

	/**
	 * 修改AI聊天记录
	 * @param aiChatHistory AI聊天记录
	 * @return 结果
	 */
	@Override
	public int updateAiChatHistory(AiChatHistory aiChatHistory) {
		return aiChatHistoryMapper.updateAiChatHistory(aiChatHistory);
	}

	/**
	 * 批量删除AI聊天记录
	 * @param chatHistoryIds 需要删除的AI聊天记录主键
	 * @return 结果
	 */
	@Override
	public int deleteAiChatHistoryByChatHistoryIds(Long[] chatHistoryIds) {
		return aiChatHistoryMapper.deleteAiChatHistoryByChatHistoryIds(chatHistoryIds);
	}

	/**
	 * 删除AI聊天记录信息
	 * @param chatHistoryId AI聊天记录主键
	 * @return 结果
	 */
	@Override
	public int deleteAiChatHistoryByChatHistoryId(Long chatHistoryId) {
		return aiChatHistoryMapper.deleteAiChatHistoryByChatHistoryId(chatHistoryId);
	}

}
