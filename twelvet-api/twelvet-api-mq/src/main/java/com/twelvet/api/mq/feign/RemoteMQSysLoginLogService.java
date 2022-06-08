package com.twelvet.api.mq.feign;

import com.twelvet.api.mq.feign.factory.RemoteMQSysLoginLogFallbackFactory;
import com.twelvet.api.system.domain.SysLoginInfo;
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
@FeignClient(contextId = "sysMQLoginLogService", value = ServiceNameConstants.MQ_SERVICE,
		fallbackFactory = RemoteMQSysLoginLogFallbackFactory.class)
public interface RemoteMQSysLoginLogService {

	/**
	 * 新增登录日志log MQ
	 * @param sysLoginInfo 发送系统登录MQ
	 * @return R<Boolean>
	 */
	@PostMapping("/api/system/loginInfo")
	R<Boolean> sendSysLoginLog(@RequestBody SysLoginInfo sysLoginInfo);

}
