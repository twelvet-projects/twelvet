package com.twelvet.server.system.controller.api;

import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.utils.poi.ExcelUtils;
import com.twelvet.server.system.service.ISysOperationLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     *
     * @param operationLog SysOperationLog
     * @return AjaxResult
     */
    @AuthIgnore
    @PostMapping
    public AjaxResult saveLog(@RequestBody SysOperationLog operationLog) {
        return json(iSysOperationLogService.insertOperationLog(operationLog));
    }

}
