package com.twelvet.framework.redis.service.constants;


/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 缓存key 常量
 */
public class CacheConstants {
    /**
     * oauth 缓存前缀
     */
    public static final String OAUTH_ACCESS = "oauth:access:";

    /**
     * oauth 客户端信息
     */
    public static final String CLIENT_DETAILS_KEY = "oauth:client:details";

    /**
     * 用户信息缓存
     */
    public static final String USER_DETAILS = "user_details";

    /**
     * 字典管理（默认缓存十分钟） cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict";
}
