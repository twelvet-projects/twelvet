package com.twelvet.server.ai.mapper;

import com.twelvet.api.ai.domain.AiModel;

import java.util.List;

/**
 * AI大模型Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-20
 */
public interface AiModelMapper {

	/**
	 * 查询AI大模型
	 * @param modelId AI大模型主键
	 * @return AI大模型
	 */
	public AiModel selectAiModelByModelId(Long modelId);

	/**
	 * 查询AI大模型列表
	 * @param aiModel AI大模型
	 * @return AI大模型集合
	 */
	public List<AiModel> selectAiModelList(AiModel aiModel);

	/**
	 * 新增AI大模型
	 * @param aiModel AI大模型
	 * @return 结果
	 */
	public int insertAiModel(AiModel aiModel);

	/**
	 * 修改AI大模型
	 * @param aiModel AI大模型
	 * @return 结果
	 */
	public int updateAiModel(AiModel aiModel);

	/**
	 * 删除AI大模型
	 * @param modelId AI大模型主键
	 * @return 结果
	 */
	public int deleteAiModelByModelId(Long modelId);

	/**
	 * 批量删除AI大模型
	 * @param modelIds 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteAiModelByModelIds(Long[] modelIds);

}
