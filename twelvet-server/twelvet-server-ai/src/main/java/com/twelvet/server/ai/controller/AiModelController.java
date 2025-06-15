package com.twelvet.server.ai.controller;

import java.util.List;

import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.api.ai.domain.vo.AiModelVO;
import com.twelvet.server.ai.service.IAiModelService;
import jakarta.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.twelvet.framework.jdbc.web.utils.PageUtils;

/**
 * AI大模型Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-20
 */
@Tag(description = "AiModelController", name = "AI大模型")
@RestController
@RequestMapping("/model")
public class AiModelController extends TWTController {

	private final IAiModelService aiModelService;

	public AiModelController(IAiModelService aiModelService) {
		this.aiModelService = aiModelService;
	}

	/**
	 * 查询AI大模型分页
	 */
	@Operation(summary = "查询AI大模型分页")
	@PreAuthorize("@role.hasPermi('system:model:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<AiModelVO>> pageQuery(AiModel aiModel) {
		return JsonResult.success(aiModelService.selectAiModelPage(aiModel));
	}

	/**
	 * 导出AI大模型列表
	 */
	@ResponseExcel(name = "AI大模型")
	@Operation(summary = "导出AI大模型列表")
	@PreAuthorize("@role.hasPermi('system:model:export')")
	@Log(service = "AI大模型", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public List<AiModel> export(AiModel aiModel) {
		return aiModelService.selectAiModelList(aiModel);
	}

	/**
	 * 获取AI大模型详细信息
	 */
	@Operation(summary = "获取AI大模型详细信息")
	@PreAuthorize("@role.hasPermi('system:model:query')")
	@GetMapping(value = "/{modelId}")
	public JsonResult<AiModel> getInfo(@PathVariable("modelId") Long modelId) {
		return JsonResult.success(aiModelService.selectAiModelByModelId(modelId));
	}

	/**
	 * 新增AI大模型
	 */
	@Operation(summary = "新增AI大模型")
	@PreAuthorize("@role.hasPermi('system:model:add')")
	@Log(service = "AI大模型", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody AiModel aiModel) {
		return json(aiModelService.insertAiModel(aiModel));
	}

	/**
	 * 修改AI大模型
	 */
	@Operation(summary = "修改AI大模型")
	@PreAuthorize("@role.hasPermi('system:model:edit')")
	@Log(service = "AI大模型", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody AiModel aiModel) {
		return json(aiModelService.updateAiModel(aiModel));
	}

	/**
	 * 删除AI大模型
	 */
	@Operation(summary = "删除AI大模型")
	@PreAuthorize("@role.hasPermi('system:model:remove')")
	@Log(service = "AI大模型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{modelIds}")
	public JsonResult<String> remove(@PathVariable Long[] modelIds) {
		return json(aiModelService.deleteAiModelByModelIds(modelIds));
	}

}
