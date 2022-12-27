package com.twelvet.server.system.utils;

import com.alibaba.fastjson.JSONArray;
import com.twelvet.api.system.domain.SysDictData;
import com.twelvet.framework.redis.service.RedisService;
import com.twelvet.framework.redis.service.constants.CacheConstants;
import com.twelvet.framework.utils.SpringContextHolder;
import com.twelvet.framework.utils.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 字典工具类
 */
public class DictUtils {

	/**
	 * 设置字典缓存
	 * @param key 参数键
	 * @param dictDatas 字典数据列表
	 */
	public static void setDictCache(String key, List<SysDictData> dictDatas) {
		SpringContextHolder.getBean(RedisService.class).setCacheObject(getCacheKey(key), dictDatas);
	}

	/**
	 * 删除指定字典缓存
	 * @param key 字典键
	 */
	public static void removeDictCache(String key) {
		SpringContextHolder.getBean(RedisService.class).deleteObject(getCacheKey(key));
	}

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
