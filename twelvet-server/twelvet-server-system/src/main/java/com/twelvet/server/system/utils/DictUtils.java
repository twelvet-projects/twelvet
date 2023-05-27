package com.twelvet.server.system.utils;

import com.twelvet.framework.redis.service.RedisService;
import com.twelvet.framework.redis.service.constants.CacheConstants;
import com.twelvet.framework.utils.SpringContextHolder;

import java.util.Collection;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 字典工具类
 */
public class DictUtils {

	/**
	 * 清空字典缓存
	 */
	public static void clearDictCache() {
		Collection<String> keys = SpringContextHolder.getBean(RedisService.class)
			.keys(CacheConstants.SYS_DICT_KEY + "*");
		SpringContextHolder.getBean(RedisService.class).deleteObject(keys);
	}

	/**
	 * 设置cache key
	 * @param configKey 参数键
	 * @return 缓存键key
	 */
	public static String getCacheKey(String configKey) {
		return CacheConstants.SYS_DICT_KEY + configKey;
	}

}
