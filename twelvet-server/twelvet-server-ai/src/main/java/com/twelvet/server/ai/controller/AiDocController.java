package com.twelvet.server.ai.controller;

import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.twelvet.server.ai.domain.AiDoc;
import com.twelvet.server.ai.domain.dto.AiDocDTO;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.server.ai.service.IAiDocService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI知识库文档Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Tag(description = "AiDocController", name = "AI知识库文档")
@RestController
@RequestMapping("/doc")
public class AiDocController extends TWTController {

	@Autowired
	private IAiDocService aiDocService;

	/**
	 * 查询AI知识库文档分页
	 */
	@Operation(summary = "查询AI知识库文档分页")
	@PreAuthorize("@role.hasPermi('ai:doc:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<AiDoc>> pageQuery(AiDoc aiDoc) {
		PageUtils.startPage();
		List<AiDoc> list = aiDocService.selectAiDocList(aiDoc);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 查询AI知识库文档列表
	 */
	@Operation(summary = "查询AI知识库文档列表")
	@PreAuthorize("@role.hasPermi('ai:doc:list')")
	@GetMapping("/list")
	public JsonResult<List<AiDoc>> listQuery(AiDoc aiDoc) {
		return JsonResult.success(aiDocService.selectAiDocList(aiDoc));
	}

	/**
	 * 导出AI知识库文档列表
	 */
	@ResponseExcel(name = "AI知识库文档")
	@Operation(summary = "导出AI知识库文档列表")
	@PreAuthorize("@role.hasPermi('ai:doc:export')")
	@Log(service = "AI知识库文档", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public List<AiDoc> export(AiDoc aiDoc) {
		return aiDocService.selectAiDocList(aiDoc);
	}

	/**
	 * 获取AI知识库文档详细信息
	 */
	@Operation(summary = "获取AI知识库文档详细信息")
	@PreAuthorize("@role.hasPermi('ai:doc:query')")
	@GetMapping(value = "/{docId}")
	public JsonResult<AiDoc> getInfo(@PathVariable("docId") Long docId) {
		return JsonResult.success(aiDocService.selectAiDocByDocId(docId));
	}

	/**
	 * 新增AI知识库文档
	 */
	@Operation(summary = "新增AI知识库文档")
	@PreAuthorize("@role.hasPermi('ai:doc:add')")
	@Log(service = "AI知识库文档", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody AiDocDTO aiDocDTO) {
		return json(aiDocService.insertAiDoc(aiDocDTO));
	}

	/**
	 * 删除AI知识库文档
	 */
	@Operation(summary = "删除AI知识库文档")
	@PreAuthorize("@role.hasPermi('ai:doc:remove')")
	@Log(service = "AI知识库文档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{docIds}")
	public JsonResult<String> remove(@PathVariable Long[] docIds) {
		return json(aiDocService.deleteAiDocByDocIds(docIds));
	}

}
