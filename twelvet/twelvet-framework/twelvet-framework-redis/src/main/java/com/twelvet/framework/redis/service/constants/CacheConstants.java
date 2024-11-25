package com.twelvet.framework.redis.service.constants;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 缓存key 常量
 */
public interface CacheConstants {

	/**
	 * oauth 缓存前缀
	 */
	String PROJECT_OAUTH_ACCESS = "token::access_token";

	/**
	 * oauth 客户端信息
	 */
	String CLIENT_DETAILS_KEY = "oauth:client:details";

	/**
	 * 用户信息缓存
	 */
	String USER_DETAILS = "user_details";

	/**
	 * 字典管理（默认缓存十分钟） cache key
	 */
	String SYS_DICT_KEY = "sys_dict";

}
