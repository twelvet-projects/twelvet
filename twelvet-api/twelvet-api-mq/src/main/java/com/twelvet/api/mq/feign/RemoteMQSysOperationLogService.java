package com.twelvet.api.mq.feign;

import com.twelvet.api.mq.feign.factory.RemotMQSysOperationLogFallbackFactory;
import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.framework.core.constants.ServiceNameConstants;
import com.twelvet.framework.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 系统登录日志
 */
@FeignClient(
        contextId = "sysMQOperationLogService",
        value = ServiceNameConstants.MQ_SERVICE,
        fallbackFactory = RemotMQSysOperationLogFallbackFactory.class
)
public interface RemoteMQSysOperationLogService {

    /**
     * 新增登录日志log MQ
     *
     * @param sysOperationLog 发送系统登录MQ
     * @return R<Boolean>
     */
    @PostMapping("/api/system/operationLog")
    R<Boolean> sendSysLoginLog(@RequestBody SysOperationLog sysOperationLog);

}
