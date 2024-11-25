package com.twelvet.server.ai.service;

import com.twelvet.server.ai.domain.AiModel;

import java.util.List;

/**
 * AI知识库Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
public interface IAiModelService {

	/**
	 * 查询AI知识库
	 * @param modelId AI知识库主键
	 * @return AI知识库
	 */
	public AiModel selectAiModelByModelId(Long modelId);

	/**
	 * 查询AI知识库列表
	 * @param aiModel AI知识库
	 * @return AI知识库集合
	 */
	public List<AiModel> selectAiModelList(AiModel aiModel);

	/**
	 * 新增AI知识库
	 * @param aiModel AI知识库
	 * @return 结果
	 */
	public int insertAiModel(AiModel aiModel);

	/**
	 * 修改AI知识库
	 * @param aiModel AI知识库
	 * @return 结果
	 */
	public int updateAiModel(AiModel aiModel);

	/**
	 * 批量删除AI知识库
	 * @param modelIds 需要删除的AI知识库主键集合
	 * @return 结果
	 */
	public int deleteAiModelByModelIds(Long[] modelIds);

}
