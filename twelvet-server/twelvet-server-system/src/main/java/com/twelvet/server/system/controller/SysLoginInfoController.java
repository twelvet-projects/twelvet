package com.twelvet.server.system.controller;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.utils.poi.ExcelUtils;
import com.twelvet.server.system.service.ISysLoginInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 系统登录日志
 */
@Tag(description = "SysLoginInfoController", name = "系统登录日志")
@RestController
@RequestMapping("/loginInfo")
public class SysLoginInfoController extends TWTController {

	@Autowired
	private ISysLoginInfoService iSysLoginInfoService;

	/**
	 * 登录日志查询
	 * @param loginInfo SysLoginInfo
	 * @return 查询数据
	 */
	@Operation(summary = "登录日志查询")
	@GetMapping("/pageQuery")
	@PreAuthorize("@role.hasPermi('system:logininfor:list')")
	public JsonResult<TableDataInfo> pageQuery(SysLoginInfo loginInfo) {
		PageUtils.startPage();
		List<SysLoginInfo> list = iSysLoginInfoService.selectLoginInfoList(loginInfo);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * 批量删除日志
	 * @param infoIds 日志Id list
	 * @return 操作结果
	 */
	@Operation(summary = "批量删除日志")
	@DeleteMapping("/{infoIds}")
	@PreAuthorize("@role.hasPermi('system:logininfor:remove')")
	public JsonResult<String> remove(@PathVariable Long[] infoIds) {
		return json(iSysLoginInfoService.deleteLoginInfoByIds(infoIds));
	}

	/**
	 * 清空登录日志
	 * @return JsonResult<String>
	 */
	@Operation(summary = "清空登录日志")
	@Log(service = "登陆日志", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clean")
	@PreAuthorize("@role.hasPermi('system:logininfor:remove')")
	public JsonResult<String> clean() {
		iSysLoginInfoService.cleanLoginInfo();
		return JsonResult.success();
	}

	/**
	 * 导出Excel
	 * @param response HttpServletResponse
	 * @param loginInfo SysLoginInfo
	 */
	@Operation(summary = "导出Excel")
	@Log(service = "登陆日志", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@PreAuthorize("@role.hasPermi('system:logininfor:export')")
	public void export(HttpServletResponse response, @RequestBody SysLoginInfo loginInfo) {
		List<SysLoginInfo> list = iSysLoginInfoService.selectLoginInfoList(loginInfo);
		ExcelUtils<SysLoginInfo> excelUtils = new ExcelUtils<>(SysLoginInfo.class);
		excelUtils.exportExcel(response, list, "登陆日志");
	}

}
