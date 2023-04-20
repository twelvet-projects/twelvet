package com.twelvet.framework.utils.http;

import com.twelvet.framework.utils.TUtils;
import com.twelvet.framework.utils.exception.TWTUtilsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: IP工具类
 */
public class IpUtils {

	public IpUtils() {
		throw new TWTUtilsException("This is a utility class and cannot be instantiated");
	}

	private static final Logger log = LoggerFactory.getLogger(IpUtils.class);

	private static final String UNKNOWN = "unknown";

	/**
	 * 获取客户端IP
	 * @return IP
	 */
	public static String getIpAddr() {
		return getIpAddr(ServletUtils.getRequest().get());
	}

	/**
	 * 获取客户端IP地址
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static String getIpAddr(HttpServletRequest request) {

		if (request == null) {
			return "unknown";
		}

		String ip = null;

		// X-Forwarded-For：Squid 服务代理
		String ipAddresses = request.getHeader("X-Forwarded-For");
		if (TUtils.isEmpty(ipAddresses) || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			// Proxy-Client-IP：apache 服务代理
			ipAddresses = request.getHeader("Proxy-Client-IP");
		}
		if (TUtils.isEmpty(ipAddresses) || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			// WL-Proxy-Client-IP：weblogic 服务代理
			ipAddresses = request.getHeader("WL-Proxy-Client-IP");
		}
		if (TUtils.isEmpty(ipAddresses) || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			// HTTP_CLIENT_IP：有些代理服务器
			ipAddresses = request.getHeader("HTTP_CLIENT_IP");
		}
		if (TUtils.isEmpty(ipAddresses) || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			// X-Real-IP：nginx服务代理
			ipAddresses = request.getHeader("X-Real-IP");
		}

		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
		if (ipAddresses != null && ipAddresses.length() != 0) {
			ip = ipAddresses.split(",")[0];
		}

		// 还是不能获取到，最后再通过request.getRemoteAddr();获取
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}

	/**
	 * 获得服务器IP
	 * @return String
	 */
	public static String getHostIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}

	/**
	 * 获取服务器名称
	 * @return String
	 */
	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		}
		catch (UnknownHostException e) {
			return "未知";
		}
	}

}
