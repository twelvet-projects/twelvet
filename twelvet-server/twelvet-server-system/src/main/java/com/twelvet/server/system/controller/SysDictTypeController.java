package com.twelvet.server.system.controller;

import com.twelvet.api.system.domain.SysDictType;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.poi.ExcelUtils;
import com.twelvet.server.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 数据字典信息
 */
@RestController
@RequestMapping("/dictionaries/type")
public class SysDictTypeController extends TWTController {

    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 数据字典信息分页查询
     *
     * @param dictType SysDictType
     * @return AjaxResult
     */
    @GetMapping("/pageQuery")
    @PreAuthorize("@role.hasPermi('system:dictionaries:list')")
    public AjaxResult pageQuery(SysDictType dictType) {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 数据字典导出
     *
     * @param response HttpServletResponse
     * @param dictType SysDictType
     */
    @Log(service = "字典类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @PreAuthorize("@role.hasPermi('system:dict:export')")
    public void export(HttpServletResponse response, @RequestBody SysDictType dictType) {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtils<SysDictType> excelUtils = new ExcelUtils<>(SysDictType.class);
        excelUtils.exportExcel(response, list, "字典类型");
    }

    /**
     * 查询字典类型详细
     *
     * @param dictId 数据字典ID
     * @return AjaxResult
     */
    @GetMapping(value = "/{dictId}")
    @PreAuthorize("@role.hasPermi('system:dict:query')")
    public AjaxResult getInfo(@PathVariable Long dictId) {
        return AjaxResult.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     *
     * @param dict SysDictType
     * @return AjaxResult
     */
    @Log(service = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    @PreAuthorize("@role.hasPermi('system:dict:insert')")
    public AjaxResult insert(@Validated @RequestBody SysDictType dict) {
        if (
                UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))
        ) {
            return AjaxResult.error(
                    "新增字典'" + dict.getDictName() + "'失败，字典类型已存在"
            );
        }
        dict.setCreateBy(SecurityUtils.getUsername());
        return json(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     *
     * @param dict SysDictType
     * @return AjaxResult
     */
    @Log(service = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    @PreAuthorize("@role.hasPermi('system:dict:update')")
    public AjaxResult update(@Validated @RequestBody SysDictType dict) {
        if (
                UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))
        ) {
            return AjaxResult.error(
                    "修改字典'" + dict.getDictName() + "'失败，字典类型已存在"
            );
        }
        dict.setUpdateBy(SecurityUtils.getUsername());
        return json(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     *
     * @param dictIds 数据字典Ids
     * @return AjaxResult
     */
    @Log(service = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    @PreAuthorize("@role.hasPermi('system:dict:remove')")
    public AjaxResult remove(@PathVariable Long[] dictIds) {
        return json(dictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * 清空缓存
     *
     * @return AjaxResult
     */
    @Log(service = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clearCache")
    @PreAuthorize("@role.hasPermi('system:dict:remove')")
    public AjaxResult clearCache() {
        dictTypeService.clearCache();
        return AjaxResult.success();
    }

    /**
     * 获取字典选择框列表
     *
     * @return AjaxResult
     */
    @GetMapping("/optionSelect")
    public AjaxResult optionSelect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return AjaxResult.success(dictTypes);
    }
}
