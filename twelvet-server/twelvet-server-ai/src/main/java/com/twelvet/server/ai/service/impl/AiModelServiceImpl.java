package com.twelvet.server.ai.service.impl;

import java.util.List;

import com.twelvet.server.ai.domain.AiModel;
import com.twelvet.server.ai.mapper.AiDocMapper;
import com.twelvet.server.ai.mapper.AiDocSliceMapper;
import com.twelvet.server.ai.mapper.AiModelMapper;
import com.twelvet.server.ai.service.IAiModelService;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AI知识库Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Service
public class AiModelServiceImpl implements IAiModelService {

	@Autowired
	private AiModelMapper aiModelMapper;

	@Autowired
	private AiDocMapper aiDocMapper;

	@Autowired
	private AiDocSliceMapper aiDocSliceMapper;

	@Autowired
	private VectorStore vectorStore;

	/**
	 * 查询AI知识库
	 * @param modelId AI知识库主键
	 * @return AI知识库
	 */
	@Override
	public AiModel selectAiModelByModelId(Long modelId) {
		return aiModelMapper.selectAiModelByModelId(modelId);
	}

	/**
	 * 查询AI知识库列表
	 * @param aiModel AI知识库
	 * @return AI知识库
	 */
	@Override
	public List<AiModel> selectAiModelList(AiModel aiModel) {
		return aiModelMapper.selectAiModelList(aiModel);
	}

	/**
	 * 新增AI知识库
	 * @param aiModel AI知识库
	 * @return 结果
	 */
	@Override
	public int insertAiModel(AiModel aiModel) {
		return aiModelMapper.insertAiModel(aiModel);
	}

	/**
	 * 修改AI知识库
	 * @param aiModel AI知识库
	 * @return 结果
	 */
	@Override
	public int updateAiModel(AiModel aiModel) {
		return aiModelMapper.updateAiModel(aiModel);
	}

	/**
	 * 批量删除AI知识库
	 * @param modelIds 需要删除的AI知识库主键
	 * @return 结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteAiModelByModelIds(Long[] modelIds) {
		int i = aiModelMapper.deleteAiModelByModelIds(modelIds);

		// TODO 删除向量数据库向量
		// vectorStore.add();

		// 批量删除文档
		aiDocMapper.deleteAiDocByModelIds(modelIds);
		// 批量删除分片
		aiDocSliceMapper.deleteAiDocSliceByModelIds(modelIds);
		return i;
	}

}
