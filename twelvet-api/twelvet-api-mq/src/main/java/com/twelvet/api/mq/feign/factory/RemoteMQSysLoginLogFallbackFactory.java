package com.twelvet.api.mq.feign.factory;


import com.twelvet.api.mq.feign.RemoteMQSysLoginLogService;
import com.twelvet.framework.core.domain.R;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 系统登录日志降级处理
 */
@Component
public class RemoteMQSysLoginLogFallbackFactory implements FallbackFactory<RemoteMQSysLoginLogService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteMQSysLoginLogFallbackFactory.class);

    @Override
    public RemoteMQSysLoginLogService create(Throwable throwable) {
        log.error("系统登录日志服务调用失败:{}", throwable.getMessage());
        return sysLoginInfo -> R.fail();
    }

}
