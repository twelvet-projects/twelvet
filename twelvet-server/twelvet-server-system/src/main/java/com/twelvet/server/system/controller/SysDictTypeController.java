package com.twelvet.server.system.controller;

import cn.twelvet.excel.annotation.ResponseExcel;
import com.twelvet.api.system.domain.SysDictType;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.server.system.service.ISysDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 数据字典信息
 */
@Tag(description = "SysDictTypeController", name = "数据字典信息")
@RestController
@RequestMapping("/dictionaries/type")
public class SysDictTypeController extends TWTController {

	@Autowired
	private ISysDictTypeService dictTypeService;

	/**
	 * 数据字典信息分页查询
	 * @param dictType SysDictType
	 * @return JsonResult<TableDataInfo>
	 */
	@Operation(summary = "数据字典信息分页查询")
	@GetMapping("/pageQuery")
	@PreAuthorize("@role.hasPermi('system:dictionaries:list')")
	public JsonResult<TableDataInfo> pageQuery(SysDictType dictType) {
		PageUtils.startPage();
		List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 数据字典导出
	 * @param dictType SysDictType
	 * @return List<SysDictType>
	 */
	@ResponseExcel(name = "字典类型")
	@Operation(summary = "数据字典导出")
	@Log(service = "字典类型", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@PreAuthorize("@role.hasPermi('system:dict:export')")
	public List<SysDictType> export(@RequestBody SysDictType dictType) {
		return dictTypeService.selectDictTypeList(dictType);
	}

	/**
	 * 查询字典类型详细
	 * @param dictId 数据字典ID
	 * @return JsonResult<SysDictType>
	 */
	@Operation(summary = "查询字典类型详细")
	@GetMapping(value = "/{dictId}")
	@PreAuthorize("@role.hasPermi('system:dict:query')")
	public JsonResult<SysDictType> getInfo(@PathVariable Long dictId) {
		return JsonResult.success(dictTypeService.selectDictTypeById(dictId));
	}

	/**
	 * 新增字典类型
	 * @param dict SysDictType
	 * @return JsonResult<String>
	 */
	@Operation(summary = "新增字典类型")
	@Log(service = "字典类型", businessType = BusinessType.INSERT)
	@PostMapping
	@PreAuthorize("@role.hasPermi('system:dict:insert')")
	public JsonResult<String> insert(@Validated @RequestBody SysDictType dict) {
		if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
			return JsonResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
		}
		dict.setCreateBy(SecurityUtils.getUsername());
		return json(dictTypeService.insertDictType(dict));
	}

	/**
	 * 修改字典类型
	 * @param dict SysDictType
	 * @return JsonResult<String>
	 */
	@Operation(summary = "修改字典类型")
	@Log(service = "字典类型", businessType = BusinessType.UPDATE)
	@PutMapping
	@PreAuthorize("@role.hasPermi('system:dict:update')")
	public JsonResult<String> update(@Validated @RequestBody SysDictType dict) {
		if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
			return JsonResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
		}
		dict.setUpdateBy(SecurityUtils.getUsername());
		return json(dictTypeService.updateDictType(dict));
	}

	/**
	 * 删除字典类型
	 * @param dictIds 数据字典Ids
	 * @return JsonResult<String>
	 */
	@Operation(summary = "删除字典类型")
	@Log(service = "字典类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dictIds}")
	@PreAuthorize("@role.hasPermi('system:dict:remove')")
	public JsonResult<String> remove(@PathVariable Long[] dictIds) {
		return json(dictTypeService.deleteDictTypeByIds(dictIds));
	}

	/**
	 * 清空缓存
	 * @return JsonResult<String>
	 */
	@Operation(summary = "清空缓存")
	@Log(service = "字典类型", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clearCache")
	@PreAuthorize("@role.hasPermi('system:dict:remove')")
	public JsonResult<String> clearCache() {
		dictTypeService.clearCache();
		return JsonResult.success();
	}

	/**
	 * 获取字典选择框列表
	 * @return JsonResult<List<SysDictType>>
	 */
	@Operation(summary = "获取字典选择框列表")
	@GetMapping("/optionSelect")
	public JsonResult<List<SysDictType>> optionSelect() {
		List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
		return JsonResult.success(dictTypes);
	}

}
