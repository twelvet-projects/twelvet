package com.twelvet.server.ai.controller;

import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.twelvet.api.ai.domain.AiKnowledge;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.server.ai.service.IAiKnowledgeService;
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
@Tag(description = "AiKnowledgeController", name = "AI知识库")
@RestController
@RequestMapping("/knowledge")
public class AiKnowledgeController extends TWTController {

	private final IAiKnowledgeService aiKnowledgeService;

	public AiKnowledgeController(IAiKnowledgeService aiKnowledgeService) {
		this.aiKnowledgeService = aiKnowledgeService;
	}

	/**
	 * 查询AI知识库分页
	 */
	@Operation(summary = "查询AI知识库分页")
	@PreAuthorize("@role.hasPermi('ai:knowledge:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<AiKnowledge>> pageQuery(AiKnowledge aiKnowledge) {
		PageUtils.startPage();
		List<AiKnowledge> list = aiKnowledgeService.selectAiknowledgeList(aiKnowledge);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 查询AI知识库列表
	 */
	@Operation(summary = "查询AI知识库列表")
	@PreAuthorize("@role.hasPermi('ai:knowledge:list')")
	@GetMapping("/list")
	public JsonResult<List<AiKnowledge>> listQuery(AiKnowledge aiKnowledge) {
		return JsonResult.success(aiKnowledgeService.selectAiknowledgeList(aiKnowledge));
	}

	/**
	 * 导出AI知识库列表
	 */
	@ResponseExcel(name = "AI知识库")
	@Operation(summary = "导出AI知识库列表")
	@PreAuthorize("@role.hasPermi('ai:knowledge:export')")
	@Log(service = "AI知识库", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public List<AiKnowledge> export(AiKnowledge aiKnowledge) {
		return aiKnowledgeService.selectAiknowledgeList(aiKnowledge);
	}

	/**
	 * 获取AI知识库详细信息
	 */
	@Operation(summary = "获取AI知识库详细信息")
	@PreAuthorize("@role.hasPermi('ai:knowledge:query')")
	@GetMapping(value = "/{knowledgeId}")
	public JsonResult<AiKnowledge> getInfo(@PathVariable("knowledgeId") Long knowledgeId) {
		return JsonResult.success(aiKnowledgeService.selectAiKnowledgeByKnowledgeId(knowledgeId));
	}

	/**
	 * 新增AI知识库
	 */
	@Operation(summary = "新增AI知识库")
	@PreAuthorize("@role.hasPermi('ai:knowledge:add')")
	@Log(service = "AI知识库", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody AiKnowledge aiKnowledge) {
		return json(aiKnowledgeService.insertAiKnowledge(aiKnowledge));
	}

	/**
	 * 修改AI知识库
	 */
	@Operation(summary = "修改AI知识库")
	@PreAuthorize("@role.hasPermi('ai:knowledge:edit')")
	@Log(service = "AI知识库", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody AiKnowledge aiKnowledge) {
		return json(aiKnowledgeService.updateAiKnowledge(aiKnowledge));
	}

	/**
	 * 删除AI知识库
	 */
	@Operation(summary = "删除AI知识库")
	@PreAuthorize("@role.hasPermi('ai:knowledge:remove')")
	@Log(service = "AI知识库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{knowledgeIds}")
	public JsonResult<String> remove(@PathVariable Long[] knowledgeIds) {
		return json(aiKnowledgeService.deleteAiKnowledgeByKnowledgeIds(knowledgeIds));
	}

}
