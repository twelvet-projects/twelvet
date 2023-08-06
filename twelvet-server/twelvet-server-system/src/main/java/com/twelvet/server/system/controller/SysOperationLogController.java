package com.twelvet.server.system.controller;

import cn.twelvet.excel.annotation.ResponseExcel;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.server.system.service.ISysOperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 操作日志记录
 */
@Tag(description = "SysOperationLogController", name = "操作日志记录")
@RestController
@RequestMapping("/operationLog")
public class SysOperationLogController extends TWTController {

	@Autowired
	private ISysOperationLogService iSysOperationLogService;

	/**
	 * 移除指定ID日志
	 * @param operationLogIds Long[]
	 * @return JsonResult<String>
	 */
	@Operation(summary = "移除指定ID日志")
	@Log(service = "操作日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{operationLogIds}")
	@PreAuthorize("@role.hasPermi('system:operlog:remove')")
	public JsonResult<String> remove(@PathVariable Long[] operationLogIds) {
		return json(iSysOperationLogService.deleteOperationLogByIds(operationLogIds));
	}

	/**
	 * 清空初始化操作日志
	 * @return JsonResult<String>
	 */
	@Operation(summary = "清空初始化操作日志")
	@Log(service = "操作日志", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clean")
	@PreAuthorize("@role.hasPermi('system:operlog:remove')")
	public JsonResult<String> clean() {
		iSysOperationLogService.cleanOperationLog();
		return JsonResult.success();
	}

	/**
	 * 分页查询
	 * @param operationLog SysOperationLog
	 * @return JsonResult<TableDataInfo>
	 */
	@Operation(summary = "分页查询")
	@GetMapping("/pageQuery")
	@PreAuthorize("@role.hasPermi('system:operlog:list')")
	public JsonResult<TableDataInfo> pageQuery(SysOperationLog operationLog) {
		PageUtils.startPage();
		List<SysOperationLog> list = iSysOperationLogService.selectOperationLogList(operationLog);
		return JsonResult.success(PageUtils.getDataTable(list));
	}

	/**
	 * Excel导出
	 * @param operationLog SysOperationLog
	 * @return List<SysOperationLog>
	 */
	@ResponseExcel(name = "操作日志")
	@Operation(summary = "Excel导出")
	@Log(service = "操作日志", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@PreAuthorize("@role.hasPermi('system:operlog:export')")
	public List<SysOperationLog> export(@RequestBody SysOperationLog operationLog) {
		return iSysOperationLogService.selectOperationLogList(operationLog);
	}

}
