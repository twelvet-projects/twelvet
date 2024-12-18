package com.twelvet.server.ai.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.twelvet.api.ai.domain.AiModel;
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
public interface AiModelMapper {

	/**
	 * 查询AI知识库
	 * @param modelId AI知识库主键
	 * @return AI知识库
	 */
	AiModel selectAiModelByModelId(Long modelId);

	/**
	 * 查询AI知识库列表
	 * @param aiModel AI知识库
	 * @return AI知识库集合
	 */
	List<AiModel> selectAiModelList(AiModel aiModel);

	/**
	 * 新增AI知识库
	 * @param aiModel AI知识库
	 * @return 结果
	 */
	int insertAiModel(AiModel aiModel);

	/**
	 * 修改AI知识库
	 * @param aiModel AI知识库
	 * @return 结果
	 */
	int updateAiModel(AiModel aiModel);

	/**
	 * 删除AI知识库
	 * @param modelId AI知识库主键
	 * @return 结果
	 */
	int deleteAiModelByModelId(Long modelId);

	/**
	 * 批量删除AI知识库
	 * @param modelIds 需要删除的数据主键集合
	 * @return 结果
	 */
	int deleteAiModelByModelIds(Long[] modelIds);

}
