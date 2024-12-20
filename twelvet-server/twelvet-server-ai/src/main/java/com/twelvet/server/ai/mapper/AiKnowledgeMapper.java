package com.twelvet.server.ai.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.twelvet.api.ai.domain.AiKnowledge;
import com.twelvet.server.ai.constant.AIDataSourceConstants;

import java.util.List;

/**
 * AI知识库Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@DS(AIDataSourceConstants.DS_MASTER)
public interface AiKnowledgeMapper {

	/**
	 * 查询AI知识库
	 * @param knowledgeId AI知识库主键
	 * @return AI知识库
	 */
	AiKnowledge selectAiKnowledgeByKnowledgeId(Long knowledgeId);

	/**
	 * 查询AI知识库列表
	 * @param aiKnowledge AI知识库
	 * @return AI知识库集合
	 */
	List<AiKnowledge> selectAiKnowledgeList(AiKnowledge aiKnowledge);

	/**
	 * 新增AI知识库
	 * @param aiKnowledge AI知识库
	 * @return 结果
	 */
	int insertAiKnowledge(AiKnowledge aiKnowledge);

	/**
	 * 修改AI知识库
	 * @param aiKnowledge AI知识库
	 * @return 结果
	 */
	int updateAiKnowledge(AiKnowledge aiKnowledge);

	/**
	 * 删除AI知识库
	 * @param knowledgeId AI知识库主键
	 * @return 结果
	 */
	int deleteAiKnowledgeByKnowledgeId(Long knowledgeId);

	/**
	 * 批量删除AI知识库
	 * @param knowledgeIds 需要删除的数据主键集合
	 * @return 结果
	 */
	int deleteAiKnowledgeByKnowledgeIds(Long[] knowledgeIds);

}
