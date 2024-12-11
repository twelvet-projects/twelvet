package com.twelvet.server.ai.service.impl;

import java.util.List;

import com.twelvet.api.ai.domain.AiChatHistoryContent;
import com.twelvet.server.ai.mapper.AiChatHistoryContentMapper;
import com.twelvet.server.ai.service.IAiChatHistoryContentService;
import org.springframework.beans.factory.annotation.Autowired;
import com.twelvet.framework.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * AI会话内容详情Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
@Service
public class AiChatHistoryContentServiceImpl implements IAiChatHistoryContentService {

	private final AiChatHistoryContentMapper aiChatHistoryContentMapper;

	public AiChatHistoryContentServiceImpl(AiChatHistoryContentMapper aiChatHistoryContentMapper) {
		this.aiChatHistoryContentMapper = aiChatHistoryContentMapper;
	}

	/**
	 * 查询AI会话内容详情
	 * @param chatHistoryContentId AI会话内容详情主键
	 * @return AI会话内容详情
	 */
	@Override
	public AiChatHistoryContent selectAiChatHistoryContentByChatHistoryContentId(Long chatHistoryContentId) {
		return aiChatHistoryContentMapper.selectAiChatHistoryContentByChatHistoryContentId(chatHistoryContentId);
	}

	/**
	 * 查询AI会话内容详情列表
	 * @param aiChatHistoryContent AI会话内容详情
	 * @return AI会话内容详情
	 */
	@Override
	public List<AiChatHistoryContent> selectAiChatHistoryContentList(AiChatHistoryContent aiChatHistoryContent) {
		return aiChatHistoryContentMapper.selectAiChatHistoryContentList(aiChatHistoryContent);
	}

	/**
	 * 新增AI会话内容详情
	 * @param aiChatHistoryContent AI会话内容详情
	 * @return 结果
	 */
	@Override
	public int insertAiChatHistoryContent(AiChatHistoryContent aiChatHistoryContent) {
		return aiChatHistoryContentMapper.insertAiChatHistoryContent(aiChatHistoryContent);
	}

	/**
	 * 修改AI会话内容详情
	 * @param aiChatHistoryContent AI会话内容详情
	 * @return 结果
	 */
	@Override
	public int updateAiChatHistoryContent(AiChatHistoryContent aiChatHistoryContent) {
		return aiChatHistoryContentMapper.updateAiChatHistoryContent(aiChatHistoryContent);
	}

	/**
	 * 批量删除AI会话内容详情
	 * @param chatHistoryContentIds 需要删除的AI会话内容详情主键
	 * @return 结果
	 */
	@Override
	public int deleteAiChatHistoryContentByChatHistoryContentIds(Long[] chatHistoryContentIds) {
		return aiChatHistoryContentMapper.deleteAiChatHistoryContentByChatHistoryContentIds(chatHistoryContentIds);
	}

	/**
	 * 删除AI会话内容详情信息
	 * @param chatHistoryContentId AI会话内容详情主键
	 * @return 结果
	 */
	@Override
	public int deleteAiChatHistoryContentByChatHistoryContentId(Long chatHistoryContentId) {
		return aiChatHistoryContentMapper.deleteAiChatHistoryContentByChatHistoryContentId(chatHistoryContentId);
	}

}
