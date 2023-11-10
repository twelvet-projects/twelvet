package com.twelvet.server.gen.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.api.gen.domain.GenTemplate;
import com.twelvet.server.gen.service.IGenTemplateService;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import cn.twelvet.excel.annotation.ResponseExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.twelvet.framework.jdbc.web.utils.PageUtils;

/**
 * 代码生成业务模板Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Tag(description = "GenTemplateController", name = "代码生成业务模板")
@RestController
@RequestMapping("/template")
public class GenTemplateController extends TWTController {

	@Autowired
	private IGenTemplateService genTemplateService;

	/**
	 * 查询代码生成业务模板分页
	 */
	@Operation(summary = "查询代码生成业务模板分页")
	@PreAuthorize("@role.hasPermi('gen:template:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<GenTemplate>> pageQuery(GenTemplate genTemplate) {
		PageUtils.startPage();
		List<GenTemplate> list = genTemplateService.selectGenTemplateList(genTemplate);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 导出代码生成业务模板列表
	 */
	@ResponseExcel(name = "代码生成业务模板")
	@Operation(summary = "导出代码生成业务模板列表")
	@PreAuthorize("@role.hasPermi('gen:template:export')")
	@Log(service = "代码生成业务模板", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public List<GenTemplate> export(GenTemplate genTemplate) {
		return genTemplateService.selectGenTemplateList(genTemplate);
	}

	/**
	 * 获取代码生成业务模板详细信息
	 */
	@Operation(summary = "获取代码生成业务模板详细信息")
	@PreAuthorize("@role.hasPermi('gen:template:query')")
	@GetMapping(value = "/{id}")
	public JsonResult<GenTemplate> getInfo(@PathVariable("id") Long id) {
		return JsonResult.success(genTemplateService.selectGenTemplateById(id));
	}

	/**
	 * 新增代码生成业务模板
	 */
	@Operation(summary = "新增代码生成业务模板")
	@PreAuthorize("@role.hasPermi('gen:template:add')")
	@Log(service = "代码生成业务模板", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody GenTemplate genTemplate) {
		return json(genTemplateService.insertGenTemplate(genTemplate));
	}

	/**
	 * 修改代码生成业务模板
	 */
	@Operation(summary = "修改代码生成业务模板")
	@PreAuthorize("@role.hasPermi('gen:template:edit')")
	@Log(service = "代码生成业务模板", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody GenTemplate genTemplate) {
		return json(genTemplateService.updateGenTemplate(genTemplate));
	}

	/**
	 * 删除代码生成业务模板
	 */
	@Operation(summary = "删除代码生成业务模板")
	@PreAuthorize("@role.hasPermi('gen:template:remove')")
	@Log(service = "代码生成业务模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public JsonResult<String> remove(@PathVariable Long[] ids) {
		return json(genTemplateService.deleteGenTemplateByIds(ids));
	}

}
