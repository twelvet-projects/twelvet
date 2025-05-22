package com.twelvet.server.ai.mapper;

import com.twelvet.api.ai.domain.AiChatHistory;
import com.twelvet.api.ai.domain.dto.SearchAiChatHistoryDTO;
import com.twelvet.api.ai.domain.vo.AiChatHistoryVO;

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
	 * 查询AI聊天记录列表
	 * @param searchAiChatHistoryDTO 搜索聊天记录
	 * @return AI聊天记录集合
	 */
	public List<AiChatHistoryVO> selectAiChatHistoryListByUserId(SearchAiChatHistoryDTO searchAiChatHistoryDTO);

	/**
	 * 新增AI聊天记录
	 * @param aiChatHistory AI聊天记录
	 * @return 结果
	 */
	public int insertAiChatHistory(AiChatHistory aiChatHistory);

}
