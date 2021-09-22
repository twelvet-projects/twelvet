package com.twelvet.framework.log.service;

import com.twelvet.api.system.feign.RemoteLogService;
import com.twelvet.api.system.domain.SysOperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 异步调用日志服务
 */
@Service
public class AsyncLogService {

    @Autowired
    private RemoteLogService remoteLogService;

    /**
     * 保存系统日志记录
     *
     * @param sysOperationLog 操作信息
     */
    @Async
    public void saveSysLog(SysOperationLog sysOperationLog) {
        remoteLogService.saveLog(sysOperationLog);
    }
}
