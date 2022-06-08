package com.twelvet.server.system.controller.api;

import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.server.system.service.ISysOperationLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 操作日志记录
 */
@Api(tags = "操作日志记录API")
@RestController
@RequestMapping("/api/operationLog")
public class SysOperationLogApi extends TWTController {

	@Autowired
	private ISysOperationLogService iSysOperationLogService;

	/**
	 * 新增操作日志
	 * @param operationLog SysOperationLog
	 * @return AjaxResult
	 */
	@AuthIgnore
	@PostMapping
	public void saveLog(@RequestBody SysOperationLog operationLog) {
		iSysOperationLogService.insertOperationLog(operationLog);
	}

}
