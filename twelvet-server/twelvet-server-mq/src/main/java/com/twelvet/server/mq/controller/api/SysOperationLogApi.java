package com.twelvet.server.mq.controller.api;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.server.mq.service.SysOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author twelvet
 * <p>
 * 发送操作日志MQ API
 */
@RestController
@RequestMapping("/api/system/operationLog")
public class SysOperationLogApi {

    @Autowired
    private SysOperationLogService sysOperationLogService;

    /**
     * 新增登录日志log MQ
     * @return R<Boolean>
     */
    @AuthIgnore
    @PostMapping
    public R<Boolean> sendSysOperationLog(@RequestBody SysOperationLog sysOperationLog) {
        sysOperationLogService.sendSysOperationLog(sysOperationLog);
        return R.ok(true);
    }

}
