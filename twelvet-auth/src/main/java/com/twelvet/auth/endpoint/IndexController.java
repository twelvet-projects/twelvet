package com.twelvet.auth.endpoint;

import com.twelvet.framework.core.application.domain.AjaxResult;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 测试第三方登录
 */
@RestController
public class IndexController {

	@GetMapping("/")
	public AjaxResult callback(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
		return AjaxResult.success(oAuth2AuthenticationToken);
	}

}
