package com.twelvet.api.auth.feign;

import com.twelvet.api.auth.feign.domain.dto.TokenDTO;
import com.twelvet.api.auth.feign.factory.RemoteTokenFallbackFactory;
import com.twelvet.framework.core.application.page.TableDataInfo;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.core.constants.ServiceNameConstants;
import com.twelvet.framework.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 令牌管理服务
 */
@FeignClient(contextId = "remoteTokenService", value = ServiceNameConstants.AUTH_SERVICE,
		fallbackFactory = RemoteTokenFallbackFactory.class)
public interface RemoteTokenService {

	/**
	 * 分页查询token 信息
	 * @param tokenDTO TokenDTO
	 * @return R<TableDataInfo>
	 */
	@GetMapping(value = "/api/token/pageQuery", headers = SecurityConstants.HEADER_FROM_IN)
	R<TableDataInfo> getTokenPage(@SpringQueryMap TokenDTO tokenDTO);

	/**
	 * 删除token
	 * @param token token
	 * @return R<Void>
	 */
	@DeleteMapping(value = "/api/token/{token}", headers = SecurityConstants.HEADER_FROM_IN)
	R<Void> removeToken(@PathVariable("token") String token);

}
