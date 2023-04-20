package com.twelvet.framework.utils;

import cn.hutool.core.text.CharPool;
import com.twelvet.framework.utils.constants.StringConstants;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 运行时工具类
 */
public class RuntimeUtils {

	private static volatile int pId = -1;

	private static final int CPU_NUM = Runtime.getRuntime().availableProcessors();

	/**
	 * 获得当前进程的PID
	 * <p>
	 * 当失败时返回-1
	 * @return pid
	 */
	public static int getPId() {
		if (pId > 0) {
			return pId;
		}
		// something like '<pid>@<hostname>', at least in SUN / Oracle JVMs
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		final int index = jvmName.indexOf(CharPool.AT);
		if (index > 0) {
			pId = NumberUtils.toInt(jvmName.substring(0, index), -1);
			return pId;
		}
		return pId;
	}

	/**
	 * 返回应用启动的时间
	 * @return {Instant}
	 */
	public static Instant getStartTime() {
		return Instant.ofEpochMilli(ManagementFactory.getRuntimeMXBean().getStartTime());
	}

	/**
	 * 返回应用启动到现在的时间
	 * @return {Duration}
	 */
	public static Duration getUpTime() {
		return Duration.ofMillis(ManagementFactory.getRuntimeMXBean().getUptime());
	}

	/**
	 * 返回输入的JVM参数列表
	 * @return jvm参数
	 */
	public static String getJvmArguments() {
		List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
		return StringUtils.join(vmArguments, StringConstants.SPACE);
	}

	/**
	 * 获取CPU核数
	 * @return cpu count
	 */
	public static int getCpuNum() {
		return CPU_NUM;
	}

}
