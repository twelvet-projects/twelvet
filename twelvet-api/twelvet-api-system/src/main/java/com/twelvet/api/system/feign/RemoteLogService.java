package com.twelvet.api.system.feign;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.api.system.feign.factory.RemoteLogFallbackFactory;
import com.twelvet.framework.core.constants.ServiceNameConstants;
import com.twelvet.framework.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 日志服务
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.SYSTEM_SERVICE,
		fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

	/**
	 * 保存系统日志
	 * @param sysOperationLog 日志实体
	 * @return 结果
	 */
	@PostMapping("/api/operationLog")
	R<Boolean> saveLog(@RequestBody SysOperationLog sysOperationLog);

	/**
	 * 保存登录记录
	 * @param sysLoginInfo 登录结果
	 * @return 结果
	 */
	@PostMapping("/api/loginInfo")
	R<Boolean> saveLoginInfo(@RequestBody SysLoginInfo sysLoginInfo);

}
