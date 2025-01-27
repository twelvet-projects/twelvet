package com.twelvet.auth.controller;

import com.twelvet.auth.service.Oauth2AuthService;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.locale.I18nUtils;
import com.twelvet.framework.core.locale.constants.LocaleSystemConstants;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.framework.security.constants.Oauth2GrantEnums;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.zhyd.oauth.model.AuthCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 第三方登录
 * <p>
 *
 * @since 2025/1/16
 */
@AuthIgnore(value = false)
@Tag(description = "Oauth2AuthController", name = "OAuth2登录优化管理")
@RestController
@RequestMapping("/login/oauth2")
public class Oauth2AuthController extends TWTController {

	@Autowired
	private Oauth2AuthService oauth2AuthService;

	/**
	 * 获取登录地址
	 * @return
	 */
	@Operation(summary = "获取登录地址")
	@GetMapping("/{oauthCode}")
	public JsonResult<String> getAuthorize(@PathVariable String oauthCode) {
		return JsonResult.success(oauthCode, oauth2AuthService.getAuthorize(oauthCode));
	}

	@Operation(summary = "测试回调")
	@GetMapping("/code/{oauthCode}")
	public JsonResult<AuthCallback> login(@PathVariable String oauthCode, AuthCallback callback) {
		return JsonResult.success(callback);
	}

}
