package com.twelvet.framework.security.service.impl;

import com.twelvet.api.system.domain.SysUser;
import com.twelvet.api.system.feign.RemoteUserService;
import com.twelvet.api.system.model.UserInfo;
import com.twelvet.framework.core.constants.SecurityConstants;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.core.domain.utils.ResUtils;
import com.twelvet.framework.core.locale.I18nUtils;
import com.twelvet.framework.redis.service.constants.CacheConstants;
import com.twelvet.framework.security.constants.Oauth2ClientEnums;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.exception.SmsCodeException;
import com.twelvet.framework.security.exception.UserFrozenException;
import com.twelvet.framework.security.service.TwUserDetailsService;
import com.twelvet.framework.utils.http.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 自定义手机登录处理
 */
public class TwTSmsDetailsServiceImpl implements TwUserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(TwTSmsDetailsServiceImpl.class);

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CacheManager cacheManager;

	/**
	 * 识别是否使用此登录器(使用clientId判断仅支持当前的clientId使用)
	 * @param clientId 目标客户端
	 * @param grantType 登录类型
	 * @return boolean
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return Oauth2ClientEnums.TWELVET.getClientId().equals(clientId) && SecurityConstants.SMS.equals(grantType);
	}

	/**
	 * 用户名称登录
	 * @param mobile String
	 * @return UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String mobile) {
		checkCode(mobile);
		mobile = "admin";
		// TODO 此处为模拟登录，需要进行接入SMS
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(mobile) != null) {
			return (LoginUser) cache.get(mobile).get();
		}
		R<UserInfo> userResult = remoteUserService.getUserInfo(mobile);
		auth(userResult, mobile);
		UserDetails userDetails = getUserDetails(userResult);

		if (cache != null) {
			cache.put(mobile, userDetails);
		}
		return userDetails;
	}

	/**
	 * 自定义账号状态检测
	 * @param userInfo userResult
	 * @param username username
	 */
	private void auth(R<UserInfo> userInfo, String username) {
		SysUser sysUser = ResUtils.of(userInfo).getData().orElseThrow(() -> {
			log.info("登录用户：{} 不存在.", username);
			return new UsernameNotFoundException("登录用户：" + username + " 不存在");
		}).getSysUser();

		if (sysUser.getStatus().equals("1")) {
			log.info("{}： 用户已被冻结.", username);
			throw new UserFrozenException("账号已被冻结");
		}
	}

	/**
	 * 检查code是否正确
	 */
	public void checkCode(String mobile) {
		String code = ServletUtils.getRequest().get().getParameter(SecurityConstants.CODE);
		// TODO 实现手机验证码校验
		if ("1234".equals(code)) {
			// return;
			throw new SmsCodeException("已完成SMS模拟登录，但为了安全起见，请自行接入SMS！！！");
		}
		log.debug("Failed to authenticate since phone code does not match stored value");
		throw new SmsCodeException(I18nUtils.getLocale("system.code.error"));
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
