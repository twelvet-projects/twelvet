package com.twelvet.framework.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 线程工具类
 */
public class ThreadUtils {

	/**
	 * Thread sleep
	 * @param millis 时长
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Thread sleep
	 * @param timeUnit TimeUnit
	 * @param timeout timeout
	 */
	public static void sleep(TimeUnit timeUnit, long timeout) {
		try {
			timeUnit.sleep(timeout);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
