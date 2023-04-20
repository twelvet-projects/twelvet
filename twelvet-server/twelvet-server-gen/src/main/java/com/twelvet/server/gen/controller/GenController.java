package com.twelvet.server.gen.controller;

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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * 查询代码生成列表
	 * @param genTable GenTable
	 * @return JsonResult<TableDataInfo>
	 */
	@Operation(summary = "查询代码生成列表")
	@GetMapping("/pageQuery")
	@PreAuthorize("@role.hasPermi('tool:gen:list')")
	public JsonResult<TableDataInfo> pageQuery(GenTable genTable) {
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
	@PreAuthorize("@role.hasPermi('tool:gen:query')")
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
	@PreAuthorize("@role.hasPermi('tool:gen:list')")
	@GetMapping("/db/list")
	public JsonResult<TableDataInfo> dataList(GenTable genTable) {
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
	public JsonResult<TableDataInfo> columnList(@PathVariable Long tableId) {
		TableDataInfo dataInfo = new TableDataInfo();
		List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
		dataInfo.setRecords(list);
		dataInfo.setTotal(list.size());
		return JsonResult.success(dataInfo);
	}

	/**
	 * 导入表结构（保存）
	 * @param tables String
	 * @return JsonResult<String>
	 */
	@Operation(summary = "导入表结构")
	@PreAuthorize("@role.hasPermi('tool:gen:list')")
	@Log(service = "代码生成", businessType = BusinessType.IMPORT)
	@PostMapping("/importTable")
	public JsonResult<String> importTableSave(String tables) {
		String[] tableNames = Convert.toStrArray(tables);
		// 查询表信息
		List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
		genTableService.importGenTable(tableList);
		return JsonResult.success();
	}

	/**
	 * 修改保存代码生成业务
	 * @param genTable GenTable
	 * @return JsonResult<String>
	 */
	@Operation(summary = "修改保存代码生成业务")
	@PreAuthorize("@role.hasPermi('tool:gen:edit')")
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
	@PreAuthorize("@role.hasPermi('tool:gen:remove')")
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
	@PreAuthorize("@role.hasPermi('tool:gen:preview')")
	@GetMapping("/preview/{tableId}")
	public AjaxResult preview(@PathVariable("tableId") Long tableId) {
		Map<String, String> dataMap = genTableService.previewCode(tableId);
		return AjaxResult.success(dataMap);
	}

	/**
	 * 生成代码（下载方式）
	 * @param response HttpServletResponse
	 * @param tableName String
	 * @throws IOException IOException
	 */
	@Operation(summary = "生成代码")
	@PreAuthorize("@role.hasPermi('tool:gen:code')")
	@Log(service = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/download/{tableName}")
	public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
		byte[] data = genTableService.downloadCode(tableName);
		genCode(response, data);
	}

	/**
	 * 生成代码（自定义路径）
	 * @param tableName String
	 * @return JsonResult<String>
	 */
	@Operation(summary = "生成代码")
	@PreAuthorize("@role.hasPermi('tool:gen:code')")
	@Log(service = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/genCode/{tableName}")
	public JsonResult<String> genCode(@PathVariable("tableName") String tableName) {
		genTableService.generatorCode(tableName);
		return JsonResult.success();
	}

	/**
	 * 同步数据库
	 * @param tableName String
	 * @return JsonResult<String>
	 */
	@Operation(summary = "同步数据库")
	@PreAuthorize("@role.hasPermi('tool:gen:edit')")
	@Log(service = "代码生成", businessType = BusinessType.UPDATE)
	@GetMapping("/synchDb/{tableName}")
	public JsonResult<String> synchDb(@PathVariable("tableName") String tableName) {
		genTableService.synchDb(tableName);
		return JsonResult.success();
	}

	/**
	 * 批量生成代码
	 * @param response HttpServletResponse
	 * @param tables String
	 * @throws IOException IOException
	 */
	@Operation(summary = "批量生成代码")
	@PreAuthorize("@role.hasPermi('tool:gen:code')")
	@Log(service = "代码生成", businessType = BusinessType.GENCODE)
	@PostMapping("/batchGenCode")
	public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
		String[] tableNames = Convert.toStrArray(tables);
		byte[] data = genTableService.downloadCode(tableNames);
		genCode(response, data);
	}

	/**
	 * 生成zip文件
	 * @param response HttpServletResponse
	 * @param data 数据
	 * @throws IOException IOException
	 */
	@Operation(summary = "生成zip文件")
	private void genCode(HttpServletResponse response, byte[] data) throws IOException {
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"twelvet.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}

}
