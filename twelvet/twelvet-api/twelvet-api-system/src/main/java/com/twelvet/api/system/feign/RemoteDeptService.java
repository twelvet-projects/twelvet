package com.twelvet.api.system.feign;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.api.system.feign.factory.RemoteDeptFallbackFactory;
import com.twelvet.api.system.feign.factory.RemoteLogFallbackFactory;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.core.constants.ServiceNameConstants;
import com.twelvet.framework.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 部门服务
 */
@FeignClient(contextId = "RemoteDeptService", value = ServiceNameConstants.SYSTEM_SERVICE,
		fallbackFactory = RemoteDeptFallbackFactory.class)
public interface RemoteDeptService {

	/**
	 * 保存系统日志
	 * @return 结果
	 */
	@PostMapping(value = "/api/dept/current/user/ids", headers = SecurityConstants.HEADER_FROM_IN)
	R<Set<Long>> selectDeptIdListByUser();

}
