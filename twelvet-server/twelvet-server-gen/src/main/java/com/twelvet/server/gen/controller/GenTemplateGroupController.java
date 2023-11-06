package com.twelvet.server.gen.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.api.gen.domain.GenTemplateGroup;
import com.twelvet.server.gen.service.IGenTemplateGroupService;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import cn.twelvet.excel.annotation.ResponseExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.twelvet.framework.jdbc.web.utils.PageUtils;

/**
 * 模板管理Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Tag(description = "GenTemplateGroupController", name = "模板管理")
@RestController
@RequestMapping("/template_group")
public class GenTemplateGroupController extends TWTController {

	@Autowired
	private IGenTemplateGroupService genTemplateGroupService;

	/**
	 * 查询模板管理列表
	 */
	@Operation(summary = "查询模板管理分页")
	@PreAuthorize("@role.hasPermi('gen:metadata:template_group:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<GenTemplateGroup>> pageQuery(GenTemplateGroup genTemplateGroup) {
		PageUtils.startPage();
		List<GenTemplateGroup> list = genTemplateGroupService.selectGenTemplateGroupList(genTemplateGroup);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 导出模板管理列表
	 */
	@ResponseExcel(name = "模板管理")
	@Operation(summary = "导出模板管理列表")
	@PreAuthorize("@role.hasPermi('gen:metadata:template_group:export')")
	@Log(service = "模板管理", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public List<GenTemplateGroup> export(GenTemplateGroup genTemplateGroup) {
		return genTemplateGroupService.selectGenTemplateGroupList(genTemplateGroup);
	}

	/**
	 * 获取模板管理详细信息
	 */
	@Operation(summary = "获取模板管理详细信息")
	@PreAuthorize("@role.hasPermi('gen:metadata:template_group:query')")
	@GetMapping(value = "/{groupId}")
	public JsonResult<GenTemplateGroup> getInfo(@PathVariable("groupId") Long groupId) {
		return JsonResult.success(genTemplateGroupService.selectGenTemplateGroupByGroupId(groupId));
	}

	/**
	 * 新增模板管理
	 */
	@Operation(summary = "新增模板管理")
	@PreAuthorize("@role.hasPermi('gen:metadata:template_group:add')")
	@Log(service = "模板管理", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody GenTemplateGroup genTemplateGroup) {
		return json(genTemplateGroupService.insertGenTemplateGroup(genTemplateGroup));
	}

	/**
	 * 修改模板管理
	 */
	@Operation(summary = "修改模板管理")
	@PreAuthorize("@role.hasPermi('gen:metadata:template_group:edit')")
	@Log(service = "模板管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody GenTemplateGroup genTemplateGroup) {
		return json(genTemplateGroupService.updateGenTemplateGroup(genTemplateGroup));
	}

	/**
	 * 删除模板管理
	 */
	@Operation(summary = "删除模板管理")
	@PreAuthorize("@role.hasPermi('gen:metadata:template_group:remove')")
	@Log(service = "模板管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupIds}")
	public JsonResult<String> remove(@PathVariable Long[] groupIds) {
		return json(genTemplateGroupService.deleteGenTemplateGroupByGroupIds(groupIds));
	}

}
