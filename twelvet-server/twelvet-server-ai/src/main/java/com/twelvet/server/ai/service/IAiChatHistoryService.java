package com.twelvet.server.ai.service;

import com.twelvet.api.ai.domain.AiChatHistory;
import com.twelvet.api.ai.domain.dto.AiChatHistoryDTO;
import com.twelvet.api.ai.domain.vo.AiChatHistoryVO;

import java.util.List;

/**
 * AI聊天记录Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
public interface IAiChatHistoryService {

	/**
	 * 查询AI聊天记录列表
	 * @param userId 用户ID
	 * @param multiRound 记忆上下文数量
	 * @return AI聊天记录集合
	 */
	public List<AiChatHistoryVO> selectAiChatHistoryListByUserId(String userId, Integer multiRound);

	/**
	 * 新增AI聊天记录
	 * @param aiChatHistoryDTO AI聊天记录
	 * @return 聊天ID
	 */
	public Long insertAiChatHistory(AiChatHistoryDTO aiChatHistoryDTO);

}
