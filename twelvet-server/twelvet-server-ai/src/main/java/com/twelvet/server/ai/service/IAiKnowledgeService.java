package com.twelvet.server.ai.service;

import com.twelvet.api.ai.domain.AiKnowledge;

import java.util.List;

/**
 * AI知识库Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
public interface IAiKnowledgeService {

	/**
	 * 查询AI知识库
	 * @param knowledgeId AI知识库主键
	 * @return AI知识库
	 */
	public AiKnowledge selectAiKnowledgeByKnowledgeId(Long knowledgeId);

	/**
	 * 查询AI知识库列表
	 * @param aiKnowledge AI知识库
	 * @return AI知识库集合
	 */
	public List<AiKnowledge> selectAiknowledgeList(AiKnowledge aiKnowledge);

	/**
	 * 新增AI知识库
	 * @param aiKnowledge AI知识库
	 * @return 结果
	 */
	public int insertAiKnowledge(AiKnowledge aiKnowledge);

	/**
	 * 修改AI知识库
	 * @param aiKnowledge AI知识库
	 * @return 结果
	 */
	public int updateAiKnowledge(AiKnowledge aiKnowledge);

	/**
	 * 批量删除AI知识库
	 * @param knowledgeIds 需要删除的AI知识库主键集合
	 * @return 结果
	 */
	public int deleteAiKnowledgeByKnowledgeIds(Long[] knowledgeIds);

}
