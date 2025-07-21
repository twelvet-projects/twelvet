package com.twelvet.framework.redis.service.constants;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 缓存key 常量
 */
public interface CacheConstants {

	/**
	 * 授权安全token前缀
	 */
	String AUTHORIZATION = "twelvet_token";

	/**
	 * oauth 缓存前缀
	 */
	String PROJECT_OAUTH_ACCESS = AUTHORIZATION + "::access_token";

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

	/**
	 * 用户数据权限缓存
	 */
	String DATA_SCOPE_CACHE = "data_scope::%s";

}
