package com.twelvet.api.mq.feign.factory;


import com.twelvet.api.mq.feign.RemoteMQSysOperationLogService;
import com.twelvet.framework.core.domain.R;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 系统操作日志降级处理
 */
@Component
public class RemotMQSysOperationLogFallbackFactory implements FallbackFactory<RemoteMQSysOperationLogService> {
    private static final Logger log = LoggerFactory.getLogger(RemotMQSysOperationLogFallbackFactory.class);

    @Override
    public RemoteMQSysOperationLogService create(Throwable throwable) {
        log.error("系统操作日志服务调用失败:{}", throwable.getMessage());
        return sysOperationLog -> R.fail();
    }

}
