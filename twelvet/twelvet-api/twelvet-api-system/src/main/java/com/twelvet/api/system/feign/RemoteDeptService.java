package com.twelvet.api.system.feign;

import com.twelvet.api.system.domain.SysDept;
import com.twelvet.api.system.feign.factory.RemoteDeptFallbackFactory;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.core.constants.ServiceNameConstants;
import com.twelvet.framework.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

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
	 * 获取当前用户持有的权限列表
	 * @return R<Set<Long>>
	 */
	@GetMapping(value = "/api/dept/current/user/ids", headers = SecurityConstants.HEADER_FROM_IN)
	R<Set<Long>> selectDeptIdListByUser();

}
