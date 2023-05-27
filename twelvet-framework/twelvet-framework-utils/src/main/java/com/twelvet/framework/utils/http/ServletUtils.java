package com.twelvet.framework.utils.http;

import com.twelvet.framework.utils.CharsetKit;
import com.twelvet.framework.utils.Convert;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.exception.TWTUtilsException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: Servlet工具
 */
public class ServletUtils {

	public ServletUtils() {
		throw new TWTUtilsException("This is a utility class and cannot be instantiated");
	}

	public static final String METHOD_DELETE = "DELETE";

	public static final String METHOD_HEAD = "HEAD";

	public static final String METHOD_GET = "GET";

	public static final String METHOD_OPTIONS = "OPTIONS";

	public static final String METHOD_POST = "POST";

	public static final String METHOD_PUT = "PUT";

	public static final String METHOD_TRACE = "TRACE";

	/**
	 * 渲染json数据
	 * @param code 客户端状态码
	 * @param json json字符串
	 */
	public static void render(int code, String json) {
		HttpServletResponse response = getResponse();
		try {
			response.setCharacterEncoding(CharsetKit.UTF_8);
			response.setStatus(code);
			response.setContentType("application/json; charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(json);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取request
	 * @return HttpServletRequest
	 */
	public static Optional<HttpServletRequest> getRequest() {
		return Optional
			.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
	}

	/**
	 * 获取当前地址完整URI(注意是服务器的IP,便于查找出错机器)
	 * @return 当前完整访问地址
	 */
	public static String getHostRequestURI() {
		HttpServletRequest request = getRequest().get();
		return IpUtils.getHostIp() + ":" + request.getServerPort() + request.getRequestURI();
	}

	/**
	 * 获取getResponse
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}

	/**
	 * 获取RequestAttributes
	 * @return ServletRequestAttributes
	 */
	public static ServletRequestAttributes getRequestAttributes() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
			.getRequestAttributes();
		return requestAttributes;
	}

	/**
	 * 获取session
	 * @return HttpSession
	 */
	public static HttpSession getSession() {
		return getRequest().get().getSession();
	}

	/**
	 * 获取json格式参数
	 * @return Map
	 */
	public static Map<String, String> getMapParam() {
		return getMapParam(getRequest().get());
	}

	/**
	 * 获取json格式参数
	 * @param httpServletRequest HttpServletRequest
	 * @return Map
	 */
	public static Map<String, String> getMapParam(HttpServletRequest httpServletRequest) {
		Map<String, String> map = new HashMap<>(6);
		// 获取所有参数名称
		Enumeration enu = httpServletRequest.getParameterNames();
		// 遍历hash
		while (enu.hasMoreElements()) {
			String paramName = (String) enu.nextElement();
			// 获取参数值
			String[] paramValues = httpServletRequest.getParameterValues(paramName);
			// 是否存在参数
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}
		return map;
	}

	/**
	 * 获取body数据
	 * @return String
	 */
	public static String getStrFromStream(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();
		try (ServletInputStream inputStream = req.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			StringBuilder stringBuilder = new StringBuilder();
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
				stringBuilder.append(charBuffer, 0, bytesRead);
			}

			return stringBuilder.toString();
		}
		catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取Integer参数
	 * @param name 参数名称
	 * @return 返回参数数据
	 */
	public static Integer getParameterToInt(String name) {
		return Convert.toInt(getRequest().get().getParameter(name));
	}

	/**
	 * 获取String参数
	 */
	public static String getParameter(String name) {
		return getRequest().get().getParameter(name);
	}

	/**
	 * 导出文件给予前端
	 * @param httpServletResponse HttpServletResponse
	 * @param file 文件
	 * @param filename 文件名称
	 */
	public static void download(HttpServletResponse httpServletResponse, byte[] file, String filename) {

		try {

			httpServletResponse.setCharacterEncoding(CharsetKit.UTF_8);
			filename = URLEncoder.encode(filename, CharsetKit.UTF_8);
			// Url编码，前台需自行还原
			filename = "attachment; filename=" + filename;
			// 设置Excel导出的名称
			httpServletResponse.setHeader("Content-Disposition", filename);
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			outputStream.write(file);

		}
		catch (IOException e) {
			throw new TWTUtilsException("文件导出出错");
		}

	}

	/**
	 * 导出文件给予前端
	 * @param file 文件
	 * @param filename 文件名称
	 */
	public static void download(byte[] file, String filename) {
		download(getResponse(), file, filename);
	}

	/**
	 * 是否是Ajax异步请求
	 */
	public static boolean isAjax() {
		HttpServletRequest request = getRequest().get();
		String accept = getRequest().get().getHeader("accept");
		if (accept != null && accept.contains("application/json")) {
			return true;
		}

		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest")) {
			return true;
		}

		String uri = request.getRequestURI();
		if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml")) {
			return true;
		}

		String ajax = request.getParameter("__ajax");

		return StringUtils.inStringIgnoreCase(ajax, "json", "xml");
	}

	/**
	 * 不为Ajax异步请求
	 */
	public static boolean isNotAjax() {
		return !isAjax();
	}

	/**
	 * 内容编码
	 * @param str 内容
	 * @return 编码后的内容
	 */
	public static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 内容解码
	 * @param str 内容
	 * @return 解码后的内容
	 */
	public static String urlDecode(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}

}
