package com.twelvet.api.system.feign.factory;


import com.twelvet.api.system.feign.RemoteLogService;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.framework.core.domain.R;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 日志服务降级处理
 */
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);

    @Override
    public RemoteLogService create(Throwable throwable) {
        log.error("日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogService() {
            @Override
            public R<Boolean> saveLog(SysOperationLog sysOperationLog) {
                return null;
            }

            @Override
            public R<Boolean> saveLoginInfo(String username, Long deptId, Integer status, String message) {
                return null;
            }
        };
    }
}
