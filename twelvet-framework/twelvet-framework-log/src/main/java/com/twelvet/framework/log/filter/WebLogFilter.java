package com.twelvet.framework.log.filter;

import com.twelvet.framework.core.constants.Constants;
import com.twelvet.framework.utils.DateUtils;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebFilter(filterName = "BaseFilter", urlPatterns = "/*")
public class WebLogFilter implements Filter {

	private final static Logger log = LoggerFactory.getLogger(WebLogFilter.class);

	/**
	 * 忽略日志输出地址
	 */
	private final static List<String> IGNORES = Arrays.asList("/actuator/health");

	@Override
	public void init(FilterConfig filterConfig) {
		log.info("过滤器初始化");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse servletResponse = (HttpServletResponse) response;

		HttpServletRequest servletRequest = (HttpServletRequest) request;

		// 忽略列表日志输出
		if (IGNORES.contains(servletRequest.getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}

		String contentType = request.getContentType();
		if (!TUtils.isEmpty(contentType)) {
			if (contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)
					|| contentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
				chain.doFilter(request, response);
				return;
			}
		}

		long startTime = System.currentTimeMillis();

		RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
		ResponseWrapper responseWrapper = new ResponseWrapper(servletResponse);

		String reqJson = requestWrapper.getBody();
		Map<String, String[]> map = request.getParameterMap();
		if (map != null && !map.isEmpty() && StringUtils.isEmpty(reqJson)) {
			reqJson = mapToString(map);
		}
		requestWrapper.setAttribute("jsonParam", reqJson);

		chain.doFilter(requestWrapper, responseWrapper);
		long endTime = System.currentTimeMillis();
		// 获取response返回的内容并重新写入response
		byte[] bytes = responseWrapper.getResponseData();
		if (bytes.length > 0) {
			response.getOutputStream().write(bytes);
		}

		// 仅输出JSON
		String responseData = new String(bytes, StandardCharsets.UTF_8);

		// 判断是否为JSON响应
		if (!JacksonUtils.isValidJson(responseData)) {
			responseData = "IGNORES";
		}

		log.info("""

				===================Request================>
				时间：{}
				地址：{}
				token：{}
				参数：{}
				方式：{}
				<===================Response================
				状态：{}
				内容：{}
				时长：{}毫秒
				============================================
				""", DateUtils.getTime(), requestWrapper.getRequestURL(),
				// 认证Token
				ServletUtils.getRequest().get().getHeader(Constants.AUTHORIZATION), reqJson, requestWrapper.getMethod(),
				responseWrapper.getStatus(), responseData, endTime - startTime

		);

	}

	private String mapToString(Map<String, String[]> map) {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			sb.append(key).append("=");
			String[] paramValues = map.get(key);
			int len = paramValues.length - 1;
			for (int i = 0; i <= len; i++) {

				sb.append(paramValues[i]);
				if (i != len) {
					sb.append(",");
				}
			}
			sb.append(";");

		}
		return sb.toString();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
