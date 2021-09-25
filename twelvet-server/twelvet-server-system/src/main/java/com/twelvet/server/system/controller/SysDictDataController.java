package com.twelvet.server.system.controller;

import com.twelvet.api.system.domain.SysDictData;
import com.twelvet.framework.jdbc.web.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.poi.ExcelUtils;
import com.twelvet.server.system.service.ISysDictDataService;
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
@RequestMapping("/dictionaries/data")
public class SysDictDataController extends TWTController {

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 分页查询数据字典
     *
     * @param sysDictData SysDictData
     * @return AjaxResult
     */
    @GetMapping("/pageQuery")
    @PreAuthorize("@role.hasPermi('system:dict:list')")
    public AjaxResult pageQuery(SysDictData sysDictData) {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(sysDictData);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出数据字典excel
     *
     * @param response    HttpServletResponse
     * @param sysDictData SysDictData
     */
    @Log(service = "字典数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @PreAuthorize("@role.hasPermi('system:dict:export')")
    public void exportExcel(HttpServletResponse response, @RequestBody SysDictData sysDictData) {
        List<SysDictData> list = dictDataService.selectDictDataList(sysDictData);
        ExcelUtils<SysDictData> excelUtils = new ExcelUtils<>(SysDictData.class);
        excelUtils.exportExcel(response, list, "字典数据");
    }

    /**
     * 查询字典数据详细
     *
     * @param dictCode 数据字典Code
     * @return AjaxResult
     */
    @GetMapping(value = "/{dictCode}")
    @PreAuthorize("@role.hasPermi('system:dict:query')")
    public AjaxResult getDictDataById(@PathVariable Long dictCode) {
        return AjaxResult.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return AjaxResult
     */
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType) {
        return AjaxResult.success(dictTypeService.selectDictDataByType(dictType));
    }

    /**
     * 新增字典类型
     *
     * @param sysDictData SysDictData
     * @return AjaxResult
     */
    @Log(service = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    @PreAuthorize("@role.hasPermi('system:dict:insert')")
    public AjaxResult insert(@Validated @RequestBody SysDictData sysDictData) {
        sysDictData.setCreateBy(SecurityUtils.getUsername());
        return json(dictDataService.insertDictData(sysDictData));
    }

    /**
     * 修改保存字典类型
     *
     * @param sysDictData SysDictData
     * @return AjaxResult
     */
    @Log(service = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    @PreAuthorize("@role.hasPermi('system:dict:update')")
    public AjaxResult update(@Validated @RequestBody SysDictData sysDictData) {
        sysDictData.setUpdateBy(SecurityUtils.getUsername());
        return json(dictDataService.updateDictData(sysDictData));
    }

    /**
     * 删除字典类型
     *
     * @param dictCodes 字典类型Codes
     * @return AjaxResult
     */
    @Log(service = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    @PreAuthorize("@role.hasPermi('system:dict:remove')")
    public AjaxResult remove(@PathVariable Long[] dictCodes) {
        return json(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
