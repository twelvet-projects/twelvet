package com.twelvet.server.mq.controller.api;

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

    /*@Autowired
    private SysOperationLogService sysOperationLogService;

    *//**
     * 新增登录日志log MQ
     * @return R<Boolean>
     *//*
    @AuthIgnore
    @PostMapping
    public R<Boolean> sendSysOperationLog(@RequestBody SysOperationLog sysOperationLog) {
        sysOperationLogService.sendSysOperationLog(sysOperationLog);
        return R.ok(true);
    }*/

}
