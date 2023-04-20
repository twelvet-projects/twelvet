package com.twelvet.framework.security.utils;

import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.security.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 安全工具类
 */
public class SecurityUtils {

	/**
	 * 获取Authentication
	 * @return Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 * @return String
	 */
	public static String getUsername() {
		return getLoginUser().getUsername();
	}

	/**
	 * 获取用户
	 * @param authentication authentication
	 * @return LoginUser
	 */
	public static LoginUser getLoginUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof LoginUser) {
			return (LoginUser) principal;
		}
		return null;
	}

	/**
	 * 获取用户
	 * @return LoginUser
	 */
	public static LoginUser getLoginUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			throw new TWTException("获取用户信息主体失败");
		}
		return getLoginUser(authentication);
	}

	/**
	 * 生成BCryptPasswordEncoder密码
	 * @param password 密码
	 * @return 加密字符串
	 */
	public static String encryptPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	/**
	 * 判断密码是否相同
	 * @param rawPassword 真实密码
	 * @param encodedPassword 加密后字符
	 * @return 结果
	 */
	public static boolean matchesPassword(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	/**
	 * 是否为管理员
	 * @param userId 用户ID
	 * @return 结果
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}

}
