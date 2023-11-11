package com.twelvet.server.gen.controller;

import com.twelvet.api.gen.domain.GenDatasourceConf;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.server.gen.service.IGenDatasourceConfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源Controller
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Tag(description = "GenDatasourceConfController", name = "数据源")
@RestController
@RequestMapping("/dsConf")
public class GenDatasourceConfController extends TWTController {

	@Autowired
	private IGenDatasourceConfService genDatasourceConfService;

	/**
	 * 查询数据源列表
	 */
	@Operation(summary = "查询数据源列表")
	@PreAuthorize("@role.hasPermi('gen:dsConf:list')")
	@GetMapping("/listQuery")
	public JsonResult<List<GenDatasourceConf>> listQuery(GenDatasourceConf genDatasourceConf) {
		return JsonResult.success(genDatasourceConfService.selectGenDatasourceConfList(genDatasourceConf));
	}

	/**
	 * 查询数据源列表
	 */
	@Operation(summary = "查询数据源分页")
	@PreAuthorize("@role.hasPermi('gen:dsConf:list')")
	@GetMapping("/pageQuery")
	public JsonResult<TableDataInfo<GenDatasourceConf>> pageQuery(GenDatasourceConf genDatasourceConf) {
		PageUtils.startPage();
		List<GenDatasourceConf> list = genDatasourceConfService.selectGenDatasourceConfList(genDatasourceConf);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 获取数据源详细信息
	 */
	@Operation(summary = "获取数据源详细信息")
	@PreAuthorize("@role.hasPermi('gen:dsConf:query')")
	@GetMapping(value = "/{id}")
	public JsonResult<GenDatasourceConf> getInfo(@PathVariable("id") Long id) {
		return JsonResult.success(genDatasourceConfService.selectGenDatasourceConfById(id));
	}

	/**
	 * 新增数据源
	 */
	@Operation(summary = "新增数据源")
	@PreAuthorize("@role.hasPermi('gen:dsConf:add')")
	@Log(service = "数据源", businessType = BusinessType.INSERT)
	@PostMapping
	public JsonResult<String> add(@RequestBody GenDatasourceConf genDatasourceConf) {
		return json(genDatasourceConfService.insertGenDatasourceConf(genDatasourceConf));
	}

	/**
	 * 修改数据源
	 */
	@Operation(summary = "修改数据源")
	@PreAuthorize("@role.hasPermi('gen:dsConf:edit')")
	@Log(service = "数据源", businessType = BusinessType.UPDATE)
	@PutMapping
	public JsonResult<String> edit(@RequestBody GenDatasourceConf genDatasourceConf) {
		return json(genDatasourceConfService.updateGenDatasourceConf(genDatasourceConf));
	}

	/**
	 * 删除数据源
	 */
	@Operation(summary = "删除数据源")
	@PreAuthorize("@role.hasPermi('gen:dsConf:remove')")
	@Log(service = "数据源", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public JsonResult<String> remove(@PathVariable Long[] ids) {
		return json(genDatasourceConfService.deleteGenDatasourceConfByIds(ids));
	}

}
