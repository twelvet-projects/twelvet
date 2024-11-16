package com.twelvet.server.ai.service.impl;

import java.util.List;

import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.server.ai.mapper.AiModelMapper;
import com.twelvet.server.ai.service.IAiModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	@Override
	public int deleteAiModelByModelIds(Long[] modelIds) {
		return aiModelMapper.deleteAiModelByModelIds(modelIds);
	}

	/**
	 * 删除AI知识库信息
	 * @param modelId AI知识库主键
	 * @return 结果
	 */
	@Override
	public int deleteAiModelByModelId(Long modelId) {
		return aiModelMapper.deleteAiModelByModelId(modelId);
	}

}
