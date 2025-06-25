package com.twelvet.server.ai.service;

import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.api.ai.domain.vo.AiModelVO;
import com.twelvet.framework.core.application.page.TableDataInfo;

import java.util.List;

/**
 * AI大模型Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-20
 */
public interface IAiModelService {

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
	 * 查询AI大模型分页
	 * @param aiModel AI大模型
	 * @return AI大模型集合
	 */
	public TableDataInfo<AiModelVO> selectAiModelPage(AiModel aiModel);

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
	 * 修改AI大模型默认状态
	 * @param aiModel AI大模型
	 * @return 结果
	 */
	public int changeStatus(AiModel aiModel);

	/**
	 * 批量删除AI大模型
	 * @param modelIds 需要删除的AI大模型主键集合
	 * @return 结果
	 */
	public int deleteAiModelByModelIds(Long[] modelIds);

	/**
	 * 删除AI大模型信息
	 * @param modelId AI大模型主键
	 * @return 结果
	 */
	public int deleteAiModelByModelId(Long modelId);

}
