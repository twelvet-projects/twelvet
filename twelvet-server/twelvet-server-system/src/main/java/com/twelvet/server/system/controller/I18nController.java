package com.twelvet.server.system.controller;

import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.twelvet.api.i18n.domain.I18n;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.server.system.service.II18nService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 国际化Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-03-28
 */
@Tag(description = "I18nController", name = "国际化")
@RestController
@RequestMapping("/i18n")
public class I18nController extends TWTController {

	@Autowired
	private II18nService i18nService;

	/**
	 * 查询国际化分页
	 */
	@Operation(summary = "查询国际化分页")
	@PreAuthorize("@role.hasPermi('system:i18n:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<I18n>> pageQuery(I18n i18n) {
		PageUtils.startPage();
		List<I18n> list = i18nService.selectI18nList(i18n);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 导出国际化列表
	 */
	@ResponseExcel(name = "国际化")
	@Operation(summary = "导出国际化列表")
	@PreAuthorize("@role.hasPermi('system:i18n:export')")
	@Log(service = "国际化", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public List<I18n> export(I18n i18n) {
		return i18nService.selectI18nList(i18n);
	}

	/**
	 * 获取国际化详细信息
	 */
	@Operation(summary = "获取国际化详细信息")
	@PreAuthorize("@role.hasPermi('system:i18n:query')")
	@GetMapping(value = "/{i18nId}")
	public JsonResult<I18n> getInfo(@PathVariable("i18nId") Long i18nId) {
		return JsonResult.success(i18nService.selectI18nByI18nId(i18nId));
	}

	/**
	 * 新增国际化
	 */
	@Operation(summary = "新增国际化")
	@PreAuthorize("@role.hasPermi('system:i18n:add')")
	@Log(service = "国际化", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody I18n i18n) {
		return json(i18nService.insertI18n(i18n));
	}

	/**
	 * 修改国际化
	 */
	@Operation(summary = "修改国际化")
	@PreAuthorize("@role.hasPermi('system:i18n:edit')")
	@Log(service = "国际化", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody I18n i18n) {
		return json(i18nService.updateI18n(i18n));
	}

	/**
	 * 删除国际化
	 */
	@Operation(summary = "删除国际化")
	@PreAuthorize("@role.hasPermi('system:i18n:remove')")
	@Log(service = "国际化", businessType = BusinessType.DELETE)
	@DeleteMapping("/{i18nIds}")
	public JsonResult<String> remove(@PathVariable Long[] i18nIds) {
		return json(i18nService.deleteI18nByI18nIds(i18nIds));
	}

}
