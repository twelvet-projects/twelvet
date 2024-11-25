package com.twelvet.server.ai.controller;

import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.server.ai.service.IAiModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI知识库Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Tag(description = "AiModelController", name = "AI知识库")
@RestController
@RequestMapping("/model")
public class AiModelController extends TWTController {

	@Autowired
	private IAiModelService aiModelService;

	/**
	 * 查询AI知识库分页
	 */
	@Operation(summary = "查询AI知识库分页")
	@PreAuthorize("@role.hasPermi('ai:model:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<AiModel>> pageQuery(AiModel aiModel) {
		PageUtils.startPage();
		List<AiModel> list = aiModelService.selectAiModelList(aiModel);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 查询AI知识库列表
	 */
	@Operation(summary = "查询AI知识库列表")
	@PreAuthorize("@role.hasPermi('ai:model:list')")
	@GetMapping("/list")
	public JsonResult<List<AiModel>> listQuery(AiModel aiModel) {
		return JsonResult.success(aiModelService.selectAiModelList(aiModel));
	}

	/**
	 * 导出AI知识库列表
	 */
	@ResponseExcel(name = "AI知识库")
	@Operation(summary = "导出AI知识库列表")
	@PreAuthorize("@role.hasPermi('ai:model:export')")
	@Log(service = "AI知识库", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public List<AiModel> export(AiModel aiModel) {
		return aiModelService.selectAiModelList(aiModel);
	}

	/**
	 * 获取AI知识库详细信息
	 */
	@Operation(summary = "获取AI知识库详细信息")
	@PreAuthorize("@role.hasPermi('ai:model:query')")
	@GetMapping(value = "/{modelId}")
	public JsonResult<AiModel> getInfo(@PathVariable("modelId") Long modelId) {
		return JsonResult.success(aiModelService.selectAiModelByModelId(modelId));
	}

	/**
	 * 新增AI知识库
	 */
	@Operation(summary = "新增AI知识库")
	@PreAuthorize("@role.hasPermi('ai:model:add')")
	@Log(service = "AI知识库", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody AiModel aiModel) {
		aiModel.setCreateBy(SecurityUtils.getUsername());
		return json(aiModelService.insertAiModel(aiModel));
	}

	/**
	 * 修改AI知识库
	 */
	@Operation(summary = "修改AI知识库")
	@PreAuthorize("@role.hasPermi('ai:model:edit')")
	@Log(service = "AI知识库", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody AiModel aiModel) {
		aiModel.setUpdateBy(SecurityUtils.getUsername());
		return json(aiModelService.updateAiModel(aiModel));
	}

	/**
	 * 删除AI知识库
	 */
	@Operation(summary = "删除AI知识库")
	@PreAuthorize("@role.hasPermi('ai:model:remove')")
	@Log(service = "AI知识库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{modelIds}")
	public JsonResult<String> remove(@PathVariable Long[] modelIds) {
		return json(aiModelService.deleteAiModelByModelIds(modelIds));
	}

}
