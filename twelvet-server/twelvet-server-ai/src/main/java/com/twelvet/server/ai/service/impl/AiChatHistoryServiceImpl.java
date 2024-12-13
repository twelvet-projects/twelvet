package com.twelvet.server.ai.service.impl;

import java.time.LocalDateTime;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.github.yitter.idgen.YitIdHelper;
import com.google.common.collect.Maps;

import com.twelvet.api.ai.domain.AiChatHistory;
import com.twelvet.api.ai.domain.AiChatHistoryContent;
import com.twelvet.api.ai.domain.dto.AiChatHistoryDTO;
import com.twelvet.api.ai.domain.vo.AiChatHistoryVO;
import com.twelvet.server.ai.mapper.AiChatHistoryContentMapper;
import com.twelvet.server.ai.mapper.AiChatHistoryMapper;
import com.twelvet.server.ai.service.IAiChatHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
	 * @param userId 用户ID
	 * @param multiRound 记忆上下文数量
	 * @return AI聊天记录集合
	 */
	@Override
	public List<AiChatHistoryVO> selectAiChatHistoryListByUserId(String userId, Integer multiRound) {
		if (multiRound <= 0) {
			return CollUtil.newArrayList();
		}
		return aiChatHistoryMapper.selectAiChatHistoryListByUserId(userId, multiRound);
	}

	/**
	 * 新增AI聊天记录
	 * @param aiChatHistoryDTO AI聊天记录
	 * @return 结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Long insertAiChatHistory(AiChatHistoryDTO aiChatHistoryDTO) {
		// 插入聊天信息
		AiChatHistory aiChatHistory = new AiChatHistory();
		aiChatHistory.setChatHistoryId(YitIdHelper.nextId());
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
		aiChatHistoryContent.setChatHistoryContentId(YitIdHelper.nextId());
		aiChatHistoryContent.setChatHistoryId(aiChatHistory.getChatHistoryId());
		aiChatHistoryContent.setContent(aiChatHistoryDTO.getContent());
		aiChatHistoryContentMapper.insertAiChatHistoryContent(aiChatHistoryContent);

		return aiChatHistory.getChatHistoryId();
	}

}
