package com.twelvet.framework.utils;

import com.twelvet.framework.utils.exception.TWTUtilsException;
import com.twelvet.framework.utils.text.UUID;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: ID生成器工具类
 */
public class IdUtils {

	public IdUtils() {
		throw new TWTUtilsException("This is a utility class and cannot be instantiated");
	}

	/**
	 * 获取随机UUID
	 * @return 随机UUID
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 简化的UUID，去掉了横线
	 * @return 简化的UUID，去掉了横线
	 */
	public static String simpleUUID() {
		return UUID.randomUUID().toString(true);
	}

	/**
	 * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
	 * @return 随机UUID
	 */
	public static String fastUUID() {
		return UUID.fastUUID().toString();
	}

	/**
	 * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
	 * @return 简化的UUID，去掉了横线
	 */
	public static String fastSimpleUUID() {
		return UUID.fastUUID().toString(true);
	}

}
