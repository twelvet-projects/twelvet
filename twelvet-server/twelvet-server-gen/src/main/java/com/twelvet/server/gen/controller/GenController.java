package com.twelvet.server.gen.controller;

import cn.hutool.core.util.StrUtil;
import com.twelvet.api.gen.domain.GenGroup;
import com.twelvet.api.gen.domain.GenTable;
import com.twelvet.api.gen.domain.GenTableColumn;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.utils.Convert;
import com.twelvet.server.gen.service.IGenTableColumnService;
import com.twelvet.server.gen.service.IGenTableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 代码生成 操作处理
 */
@Tag(description = "GenController", name = "代码生成")
@RequestMapping
@RestController
public class GenController extends TWTController {

	@Autowired
	private IGenTableService genTableService;

	@Autowired
	private IGenTableColumnService genTableColumnService;

	/**
	 * 查询代码生成业务所有模板分组列表
	 */
	@Operation(summary = "查询代码生成业务所有模板分组列表")
	@PreAuthorize("@role.hasPermi('gen:list')")
	@GetMapping("/selectGenGroupAll")
	public JsonResult<List<GenGroup>> selectGenGroupAll() {
		return JsonResult.success(genTableColumnService.selectGenGroupAll());
	}

	/**
	 * 查询代码生成列表
	 * @param genTable GenTable
	 * @return JsonResult<TableDataInfo>
	 */
	@Operation(summary = "查询代码生成列表")
	@GetMapping("/pageQuery")
	@PreAuthorize("@role.hasPermi('gen:list')")
	public JsonResult<TableDataInfo<GenTable>> pageQuery(GenTable genTable) {
		PageUtils.startPage();
		List<GenTable> list = genTableService.selectGenTableList(genTable);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 获取代码生成信息
	 * @param tableId Long
	 * @return AjaxResult
	 */
	@Operation(summary = "获取代码生成信息")
	@GetMapping(value = "/{tableId}")
	@PreAuthorize("@role.hasPermi('gen:query')")
	public AjaxResult getInfo(@PathVariable Long tableId) {
		GenTable table = genTableService.selectGenTableById(tableId);
		List<GenTable> tables = genTableService.selectGenTableAll();
		List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
		Map<String, Object> map = new HashMap<>(4);
		map.put("info", table);
		map.put("rows", list);
		map.put("tables", tables);
		return AjaxResult.success(map);
	}

	/**
	 * 查询数据库列表
	 * @return JsonResult<TableDataInfo>
	 */
	@Operation(summary = "查询数据库列表")
	@PreAuthorize("@role.hasPermi('gen:list')")
	@GetMapping("/db/list")
	public JsonResult<TableDataInfo<GenTable>> dataList(GenTable genTable) {
		PageUtils.startPage();
		List<GenTable> list = genTableService.selectDbTableList(genTable);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 查询数据表字段列表
	 * @param tableId Long
	 * @return JsonResult<TableDataInfo>
	 */
	@Operation(summary = "查询数据表字段列表")
	@GetMapping(value = "/column/{tableId}")
	public JsonResult<TableDataInfo<GenTableColumn>> columnList(@PathVariable Long tableId) {
		List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
		TableDataInfo<GenTableColumn> dataInfo = TableDataInfo.page(list, list.size());
		return JsonResult.success(dataInfo);
	}

	/**
	 * 导入表结构（保存）
	 * @param tables String
	 * @return JsonResult<String>
	 */
	@Operation(summary = "导入表结构")
	@PreAuthorize("@role.hasPermi('gen:list')")
	@Log(service = "代码生成", businessType = BusinessType.IMPORT)
	@PostMapping("/importTable/{dsName}")
	public JsonResult<String> importTableSave(@PathVariable String dsName, String tables) {
		String[] tableNames = Convert.toStrArray(tables);
		// 查询表信息
		List<GenTable> tableList = genTableService.selectDbTableListByNames(dsName, tableNames);
		genTableService.importGenTable(tableList);
		return JsonResult.success();
	}

	/**
	 * 修改保存代码生成业务
	 * @param genTable GenTable
	 * @return JsonResult<String>
	 */
	@Operation(summary = "修改保存代码生成业务")
	@PreAuthorize("@role.hasPermi('gen:edit')")
	@Log(service = "代码生成", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> editSave(@Validated @RequestBody GenTable genTable) {
		genTableService.validateEdit(genTable);
		genTableService.updateGenTable(genTable);
		return JsonResult.success();
	}

	/**
	 * 删除代码生成
	 * @param tableIds Long[]
	 * @return JsonResult<String>
	 */
	@Operation(summary = "删除代码生成")
	@PreAuthorize("@role.hasPermi('gen:remove')")
	@Log(service = "代码生成", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tableIds}")
	public JsonResult<String> remove(@PathVariable Long[] tableIds) {
		genTableService.deleteGenTableByIds(tableIds);
		return JsonResult.success();
	}

	/**
	 * 预览代码
	 * @param tableId Long
	 * @return AjaxResult
	 */
	@Operation(summary = "预览代码")
	@PreAuthorize("@role.hasPermi('gen:preview')")
	@GetMapping("/preview/{tableId}")
	public AjaxResult preview(@PathVariable("tableId") Long tableId) {
		return AjaxResult.success(genTableService.previewCode(tableId));
	}

	/**
	 * 生成代码（下载方式）
	 * @param response HttpServletResponse
	 * @param tableId String
	 * @throws IOException IOException
	 */
	@Operation(summary = "生成代码（下载方式）")
	@PreAuthorize("@role.hasPermi('gen:code')")
	@Log(service = "代码生成", businessType = BusinessType.GENCODE)
	@PostMapping("/download/{tableId}")
	public void download(HttpServletResponse response, @PathVariable Long tableId) throws IOException {
		byte[] data = genTableService.downloadCode(tableId);
		genCode(response, data);
	}

	/**
	 * 生成代码（自定义路径）
	 * @param tableId 需要生成的表ID
	 * @return JsonResult<String>
	 */
	@Operation(summary = "生成代码（自定义路径）")
	@PreAuthorize("@role.hasPermi('gen:code')")
	@Log(service = "代码生成", businessType = BusinessType.GENCODE)
	@PostMapping("/genCode/{tableId}")
	public JsonResult<String> genCode(@PathVariable Long tableId) {
		genTableService.generatorCode(tableId);
		return JsonResult.success();
	}

	/**
	 * 同步数据库
	 * @param tableId 同步表ID
	 * @return JsonResult<String>
	 */
	@Operation(summary = "同步数据库")
	@PreAuthorize("@role.hasPermi('gen:edit')")
	@Log(service = "代码生成", businessType = BusinessType.UPDATE)
	@PostMapping("/synchDb/{tableId}")
	public JsonResult<String> synchDb(@PathVariable Long tableId) {
		genTableService.synchDb(tableId);
		return JsonResult.success();
	}

	/**
	 * 批量生成代码
	 * @param response HttpServletResponse
	 * @param tableIds String
	 * @throws IOException IOException
	 */
	@Operation(summary = "批量生成代码")
	@PreAuthorize("@role.hasPermi('gen:code')")
	@Log(service = "代码生成", businessType = BusinessType.GENCODE)
	@PostMapping("/batchGenCode")
	public void batchGenCode(HttpServletResponse response, String tableIds) throws IOException {
		List<Long> collect = Arrays.stream(tableIds.split(StrUtil.COMMA))
			.map(Long::parseLong)
			.collect(Collectors.toList());

		byte[] data = genTableService.downloadCode(collect);
		genCode(response, data);
	}

	/**
	 * 生成zip文件
	 * @param response HttpServletResponse
	 * @param data 数据
	 * @throws IOException IOException
	 */
	private void genCode(HttpServletResponse response, byte[] data) throws IOException {
		response.reset();
		response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"twelvet.zip\"");
		response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}

}
