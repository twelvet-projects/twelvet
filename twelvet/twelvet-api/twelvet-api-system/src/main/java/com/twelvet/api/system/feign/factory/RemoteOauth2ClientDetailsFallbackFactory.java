package com.twelvet.api.system.feign.factory;

import com.twelvet.api.system.domain.SysClientDetails;
import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.api.system.feign.RemoteLogService;
import com.twelvet.api.system.domain.SysOperationLog;
import com.twelvet.api.system.feign.RemoteOauth2ClientDetailsService;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: Oauth2服务降级处理
 */
@Component
public class RemoteOauth2ClientDetailsFallbackFactory implements FallbackFactory<RemoteOauth2ClientDetailsService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteOauth2ClientDetailsFallbackFactory.class);

	@Override
	public RemoteOauth2ClientDetailsService create(Throwable throwable) {
		log.error("Oauth2服务调用失败:{}", throwable.getMessage());
		return new RemoteOauth2ClientDetailsService() {

			@Override
			public R<SysClientDetails> getClientDetailsById(String clientId) {
				return R.fail();
			}
		};
	}

}
