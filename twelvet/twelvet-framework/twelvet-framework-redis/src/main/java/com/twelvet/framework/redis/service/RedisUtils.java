package com.twelvet.framework.redis.service;

import com.twelvet.framework.utils.SpringContextHolder;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: redis缓存工具
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class RedisUtils {

	private static final RedisTemplate redisTemplate = SpringContextHolder.getBean("redisTemplate");

	/**
	 * 判断是否存在key
	 * @param key 需要查询的key
	 * @return 是否存在
	 */
	public static Boolean hasKey(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 * @param key 缓存的键值
	 * @param value 缓存的值
	 */
	public static <T> void setCacheObject(final String key, final T value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 * @param key 缓存的键值
	 * @param value 缓存的值
	 * @param timeout 时间
	 * @param timeUnit 时间颗粒度
	 */
	public static <T> void setCacheObject(final String key, final T value, final long timeout,
			final TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
	}

	/**
	 * 获得key剩余存活时间
	 * @param key 缓存键值
	 * @return 剩余存活时间
	 */
	public static long getExpire(final String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 设置有效时间
	 * @param key Redis键
	 * @param timeout 超时时间
	 * @return true=设置成功；false=设置失败
	 */
	public static boolean expire(final String key, final long timeout) {
		return expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 设置有效时间
	 * @param key Redis键
	 * @param timeout 超时时间
	 * @param unit 时间单位
	 * @return true=设置成功；false=设置失败
	 */
	public static boolean expire(final String key, final long timeout, final TimeUnit unit) {
		return redisTemplate.expire(key, timeout, unit);
	}

	/**
	 * 获得缓存的基本对象。
	 * @param key 缓存键值
	 * @return 缓存键值对应的数据
	 */
	public static <T> T getCacheObject(final String key) {
		ValueOperations<String, T> operation = redisTemplate.opsForValue();
		return operation.get(key);
	}

	/**
	 * 删除单个对象
	 * @param key key
	 */
	public static boolean deleteObject(final String key) {
		return redisTemplate.delete(key);
	}

	/**
	 * 删除集合对象
	 * @param collection 多个对象
	 * @return 删除数量
	 */
	public static long deleteObject(final Collection collection) {
		return redisTemplate.delete(collection);
	}

	/**
	 * 缓存List数据
	 * @param key 缓存的键值
	 * @param dataList 待缓存的List数据
	 * @return 缓存的对象
	 */
	public static <T> long setCacheList(final String key, final List<T> dataList) {
		Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
		return count == null ? 0 : count;
	}

	/**
	 * 获得缓存的list对象
	 * @param key 缓存的键值
	 * @return 缓存键值对应的数据
	 */
	public static <T> List<T> getCacheList(final String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * 缓存Set
	 * @param key 缓存键值
	 * @param dataSet 缓存的数据
	 * @return 缓存数据的对象
	 */
	public static <T> long setCacheSet(final String key, final Set<T> dataSet) {
		Long count = redisTemplate.opsForSet().add(key, dataSet);
		return count == null ? 0 : count;
	}

	/**
	 * 获得缓存的set
	 * @param key key
	 * @return set对象
	 */
	public static <T> Set<T> getCacheSet(final String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * 缓存Map
	 * @param key key
	 * @param dataMap map对象
	 */
	public static <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
		if (dataMap != null) {
			redisTemplate.opsForHash().putAll(key, dataMap);
		}
	}

	/**
	 * 获得缓存的Map
	 * @param key key
	 * @return 返回map对象
	 */
	public static <T> Map<String, T> getCacheMap(final String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 往Hash中存入数据
	 * @param key Redis键
	 * @param hKey Hash键
	 * @param value 值
	 */
	public static <T> void setCacheMapValue(final String key, final String hKey, final T value) {
		redisTemplate.opsForHash().put(key, hKey, value);
	}

	/**
	 * 获取Hash中的数据
	 * @param key Redis键
	 * @param hKey Hash键
	 * @return Hash中的对象
	 */
	public static <T> T getCacheMapValue(final String key, final String hKey) {
		HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
		return opsForHash.get(key, hKey);
	}

	/**
	 * 获取多个Hash中的数据
	 * @param key Redis键
	 * @param hKeys Hash键集合
	 * @return Hash对象集合
	 */
	public static <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
		return redisTemplate.opsForHash().multiGet(key, hKeys);
	}

	/**
	 * 获得缓存的基本对象列表
	 * @param pattern 字符串前缀
	 * @return 对象列表
	 */
	public static Collection<String> keys(final String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 向队列右则推送数据
	 * @param key 队列键名
	 * @param values 推送的数据
	 */
	public static <T> void rPush(final String key, final T... values) {
		redisTemplate.opsForList().rightPushAll(key, values);
	}

	/**
	 * 从队列左则提取数据
	 * @param key 队列键名
	 * @return 数组中的最左数据
	 */
	public static <T> T lPop(final String key) {
		return (T) redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 获取列表长度
	 * @param key 查询key
	 * @return Long
	 */
	public static Long lSize(String key) {
		return redisTemplate.opsForList().size(key);
	}

}
