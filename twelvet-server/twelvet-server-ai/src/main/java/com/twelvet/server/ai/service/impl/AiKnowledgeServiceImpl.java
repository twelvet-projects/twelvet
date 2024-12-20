package com.twelvet.server.ai.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import com.twelvet.api.ai.domain.AiKnowledge;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.server.ai.mapper.AiDocMapper;
import com.twelvet.server.ai.mapper.AiDocSliceMapper;
import com.twelvet.server.ai.mapper.AiKnowledgeMapper;
import com.twelvet.server.ai.service.IAiKnowledgeService;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

/**
 * AI知识库Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Service
public class AiKnowledgeServiceImpl implements IAiKnowledgeService {

	private final AiKnowledgeMapper aiKnowledgeMapper;

	private final AiDocMapper aiDocMapper;

	private final AiDocSliceMapper aiDocSliceMapper;

	private final VectorStore vectorStore;

	public AiKnowledgeServiceImpl(AiKnowledgeMapper aiKnowledgeMapper, AiDocMapper aiDocMapper, AiDocSliceMapper aiDocSliceMapper,
								  VectorStore vectorStore) {
		this.aiKnowledgeMapper = aiKnowledgeMapper;
		this.aiDocMapper = aiDocMapper;
		this.aiDocSliceMapper = aiDocSliceMapper;
		this.vectorStore = vectorStore;
	}

	/**
	 * 查询AI知识库
	 * @param knowledgeId AI知识库主键
	 * @return AI知识库
	 */
	@Override
	public AiKnowledge selectAiKnowledgeByKnowledgeId(Long knowledgeId) {
		return aiKnowledgeMapper.selectAiKnowledgeByKnowledgeId(knowledgeId);
	}

	/**
	 * 查询AI知识库列表
	 * @param aiKnowledge AI知识库
	 * @return AI知识库
	 */
	@Override
	public List<AiKnowledge> selectAiknowledgeList(AiKnowledge aiKnowledge) {
		return aiKnowledgeMapper.selectAiKnowledgeList(aiKnowledge);
	}

	/**
	 * 新增AI知识库
	 * @param aiKnowledge AI知识库
	 * @return 结果
	 */
	@Override
	public int insertAiKnowledge(AiKnowledge aiKnowledge) {
		LocalDateTime nowDate = LocalDateTime.now();
		String username = SecurityUtils.getUsername();

		aiKnowledge.setCreateBy(username);
		aiKnowledge.setCreateTime(nowDate);
		aiKnowledge.setUpdateTime(nowDate);
		aiKnowledge.setUpdateBy(username);
		return aiKnowledgeMapper.insertAiKnowledge(aiKnowledge);
	}

	/**
	 * 修改AI知识库
	 * @param aiKnowledge AI知识库
	 * @return 结果
	 */
	@Override
	public int updateAiKnowledge(AiKnowledge aiKnowledge) {
		LocalDateTime nowDate = LocalDateTime.now();

		aiKnowledge.setUpdateBy(SecurityUtils.getUsername());
		aiKnowledge.setUpdateTime(nowDate);
		return aiKnowledgeMapper.updateAiKnowledge(aiKnowledge);
	}

	/**
	 * 批量删除AI知识库
	 * @param knowledgeIds 需要删除的AI知识库主键
	 * @return 结果
	 */
	@Override
	public int deleteAiKnowledgeByKnowledgeIds(Long[] knowledgeIds) {
		int i = aiKnowledgeMapper.deleteAiKnowledgeByKnowledgeIds(knowledgeIds);

		List<String> vectorIdList = aiDocSliceMapper.selectAiDocSliceVectorIdByKnowledgeIds(knowledgeIds);
		// 批量删除文档
		aiDocMapper.deleteAiDocByKnowledgeIds(knowledgeIds);
		// 批量删除分片
		aiDocSliceMapper.deleteAiDocSliceByKnowledgeIds(knowledgeIds);

		// 删除向量数据库向量
		if (CollectionUtil.isNotEmpty(vectorIdList)) {
			vectorStore.delete(vectorIdList);
		}
		return i;
	}

}
