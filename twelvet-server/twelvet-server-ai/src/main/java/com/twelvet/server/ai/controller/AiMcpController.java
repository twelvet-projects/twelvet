package com.twelvet.server.ai.controller;

import java.util.List;

import com.twelvet.api.ai.domain.AiMcp;
import com.twelvet.server.ai.service.IAiMcpService;
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
 * AI MCP服务Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-03-31
 */
@Tag(description = "AiMcpController", name = "AI MCP服务")
@RestController
@RequestMapping("/mcp")
public class AiMcpController extends TWTController {

	@Autowired
	private IAiMcpService aiMcpService;

	/**
	 * 查询AI MCP服务分页
	 */
	@Operation(summary = "查询AI MCP服务分页")
	@PreAuthorize("@role.hasPermi('ai:mcp:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<AiMcp>> pageQuery(AiMcp aiMcp) {
		PageUtils.startPage();
		List<AiMcp> list = aiMcpService.selectAiMcpList(aiMcp);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 导出AI MCP服务列表
	 */
	@ResponseExcel(name = "AI MCP服务")
	@Operation(summary = "导出AI MCP服务列表")
	@PreAuthorize("@role.hasPermi('ai:mcp:export')")
	@Log(service = "AI MCP服务", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public List<AiMcp> export(AiMcp aiMcp) {
		return aiMcpService.selectAiMcpList(aiMcp);
	}

	/**
	 * 获取AI MCP服务详细信息
	 */
	@Operation(summary = "获取AI MCP服务详细信息")
	@PreAuthorize("@role.hasPermi('ai:mcp:query')")
	@GetMapping(value = "/{mcpId}")
	public JsonResult<AiMcp> getInfo(@PathVariable("mcpId") Long mcpId) {
		return JsonResult.success(aiMcpService.selectAiMcpByMcpId(mcpId));
	}

	/**
	 * 新增AI MCP服务
	 */
	@Operation(summary = "新增AI MCP服务")
	@PreAuthorize("@role.hasPermi('ai:mcp:add')")
	@Log(service = "AI MCP服务", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody AiMcp aiMcp) {
		return json(aiMcpService.insertAiMcp(aiMcp));
	}

	/**
	 * 修改AI MCP服务
	 */
	@Operation(summary = "修改AI MCP服务")
	@PreAuthorize("@role.hasPermi('ai:mcp:edit')")
	@Log(service = "AI MCP服务", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody AiMcp aiMcp) {
		return json(aiMcpService.updateAiMcp(aiMcp));
	}

	/**
	 * 删除AI MCP服务
	 */
	@Operation(summary = "删除AI MCP服务")
	@PreAuthorize("@role.hasPermi('ai:mcp:remove')")
	@Log(service = "AI MCP服务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{mcpIds}")
	public JsonResult<String> remove(@PathVariable Long[] mcpIds) {
		return json(aiMcpService.deleteAiMcpByMcpIds(mcpIds));
	}

}
