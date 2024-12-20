package com.twelvet.server.ai.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;

import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.framework.utils.DateUtils;
import com.twelvet.server.ai.mapper.AiModelMapper;
import com.twelvet.server.ai.service.IAiModelService;
import org.springframework.beans.factory.annotation.Autowired;
import com.twelvet.framework.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * AI大模型Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-20
 */
@Service
public class AiModelServiceImpl implements IAiModelService {

	@Autowired
	private AiModelMapper aiModelMapper;

	/**
	 * 查询AI大模型
	 * @param modelId AI大模型主键
	 * @return AI大模型
	 */
	@Override
	public AiModel selectAiModelByModelId(Long modelId) {
		return aiModelMapper.selectAiModelByModelId(modelId);
	}

	/**
	 * 查询AI大模型列表
	 * @param aiModel AI大模型
	 * @return AI大模型
	 */
	@Override
	public List<AiModel> selectAiModelList(AiModel aiModel) {
		return aiModelMapper.selectAiModelList(aiModel);
	}

	/**
	 * 新增AI大模型
	 * @param aiModel AI大模型
	 * @return 结果
	 */
	@Override
	public int insertAiModel(AiModel aiModel) {
		LocalDateTime nowDate = LocalDateTime.now();
		aiModel.setCreateTime(nowDate);
		aiModel.setUpdateTime(nowDate);
		String loginUsername = SecurityUtils.getUsername();
		aiModel.setCreateBy(loginUsername);
		aiModel.setUpdateBy(loginUsername);
		return aiModelMapper.insertAiModel(aiModel);
	}

	/**
	 * 修改AI大模型
	 * @param aiModel AI大模型
	 * @return 结果
	 */
	@Override
	public int updateAiModel(AiModel aiModel) {
		aiModel.setUpdateTime(LocalDateTime.now());
		String loginUsername = SecurityUtils.getUsername();
		aiModel.setCreateBy(loginUsername);
		aiModel.setUpdateBy(loginUsername);
		return aiModelMapper.updateAiModel(aiModel);
	}

	/**
	 * 批量删除AI大模型
	 * @param modelIds 需要删除的AI大模型主键
	 * @return 结果
	 */
	@Override
	public int deleteAiModelByModelIds(Long[] modelIds) {
		return aiModelMapper.deleteAiModelByModelIds(modelIds);
	}

	/**
	 * 删除AI大模型信息
	 * @param modelId AI大模型主键
	 * @return 结果
	 */
	@Override
	public int deleteAiModelByModelId(Long modelId) {
		return aiModelMapper.deleteAiModelByModelId(modelId);
	}

}
