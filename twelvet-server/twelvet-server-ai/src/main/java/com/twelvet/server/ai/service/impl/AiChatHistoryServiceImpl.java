package com.twelvet.server.ai.service.impl;

import java.time.LocalDateTime;
import com.google.common.collect.Maps;

import com.twelvet.api.ai.domain.AiChatHistory;
import com.twelvet.api.ai.domain.AiChatHistoryContent;
import com.twelvet.api.ai.domain.dto.AiChatHistoryDTO;
import com.twelvet.server.ai.mapper.AiChatHistoryContentMapper;
import com.twelvet.server.ai.mapper.AiChatHistoryMapper;
import com.twelvet.server.ai.service.IAiChatHistoryService;
import org.springframework.stereotype.Service;

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

	private final AiChatHistoryMapper aiChatHistoryMapper;

	private final AiChatHistoryContentMapper aiChatHistoryContentMapper;

	public AiChatHistoryServiceImpl(AiChatHistoryMapper aiChatHistoryMapper,
			AiChatHistoryContentMapper aiChatHistoryContentMapper) {
		this.aiChatHistoryMapper = aiChatHistoryMapper;
		this.aiChatHistoryContentMapper = aiChatHistoryContentMapper;
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
	 * @param aiChatHistoryDTO AI聊天记录
	 * @return 结果
	 */
	@Override
	public Long insertAiChatHistory(AiChatHistoryDTO aiChatHistoryDTO) {
		// 插入聊天信息
		AiChatHistory aiChatHistory = new AiChatHistory();
		aiChatHistory.setMsgId(aiChatHistoryDTO.getMsgId());
		aiChatHistory.setUserId(aiChatHistoryDTO.getUserId());
		aiChatHistory.setSendUserId(aiChatHistoryDTO.getSendUserId());
		aiChatHistory.setSendUserName(aiChatHistoryDTO.getSendUserName());
		aiChatHistory.setCreateByType(aiChatHistoryDTO.getCreateByType());
		aiChatHistory.setCreateTime(aiChatHistoryDTO.getCreateTime());
		aiChatHistory.setDelFlag(Boolean.FALSE);
		aiChatHistoryMapper.insertAiChatHistory(aiChatHistory);

		// 插入聊天内容
		AiChatHistoryContent aiChatHistoryContent = new AiChatHistoryContent();
		aiChatHistoryContent.setChatHistoryId(aiChatHistory.getChatHistoryId());
		aiChatHistoryContent.setContent(aiChatHistoryDTO.getContent());
		aiChatHistoryContentMapper.insertAiChatHistoryContent(aiChatHistoryContent);

		return aiChatHistory.getChatHistoryId();
	}

}
