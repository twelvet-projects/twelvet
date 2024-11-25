package com.twelvet.api.system.feign;

import com.twelvet.api.system.domain.SysClientDetails;
import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.api.system.feign.factory.RemoteLogFallbackFactory;
import com.twelvet.api.system.feign.factory.RemoteOauth2ClientDetailsFallbackFactory;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.core.constants.ServiceNameConstants;
import com.twelvet.framework.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: Oauth2服务
 */
@FeignClient(contextId = "RemoteOauth2ClientDetailsService", value = ServiceNameConstants.SYSTEM_SERVICE,
		fallbackFactory = RemoteOauth2ClientDetailsFallbackFactory.class)
public interface RemoteOauth2ClientDetailsService {

	/**
	 * 获取终端配置详细信息
	 * @param clientId 终端ID
	 * @return JsonResult
	 */
	@GetMapping(value = "/api/client/{clientId}", headers = SecurityConstants.HEADER_FROM_IN)
	R<SysClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId);

}
