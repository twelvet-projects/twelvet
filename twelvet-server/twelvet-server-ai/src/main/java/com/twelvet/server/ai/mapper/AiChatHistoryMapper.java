package com.twelvet.server.ai.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.twelvet.api.ai.domain.AiChatHistory;
import com.twelvet.api.ai.domain.vo.AiChatHistoryVO;
import com.twelvet.framework.datasource.support.DataSourceConstants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI聊天记录Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-10
 */
@DS(DataSourceConstants.DS_MASTER)
public interface AiChatHistoryMapper {

	/**
	 * 查询AI聊天记录列表
	 * @param userId 用户ID
	 * @param multiRound 记忆上下文数量
	 * @return AI聊天记录集合
	 */
	public List<AiChatHistoryVO> selectAiChatHistoryListByUserId(@Param("userId") String userId,
			@Param("multiRound") Integer multiRound);

	/**
	 * 新增AI聊天记录
	 * @param aiChatHistory AI聊天记录
	 * @return 结果
	 */
	public int insertAiChatHistory(AiChatHistory aiChatHistory);

}
