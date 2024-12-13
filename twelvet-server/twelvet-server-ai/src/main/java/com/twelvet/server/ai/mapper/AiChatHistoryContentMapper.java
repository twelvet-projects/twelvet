package com.twelvet.server.ai.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.twelvet.api.ai.domain.AiChatHistoryContent;
import com.twelvet.server.ai.constant.AIDataSourceConstants;

import java.util.List;

/**
 * AI会话内容详情Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
@DS(AIDataSourceConstants.DS_MASTER)
public interface AiChatHistoryContentMapper {

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
	 * 删除AI会话内容详情
	 * @param chatHistoryContentId AI会话内容详情主键
	 * @return 结果
	 */
	public int deleteAiChatHistoryContentByChatHistoryContentId(Long chatHistoryContentId);

	/**
	 * 批量删除AI会话内容详情
	 * @param chatHistoryContentIds 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteAiChatHistoryContentByChatHistoryContentIds(Long[] chatHistoryContentIds);

}
