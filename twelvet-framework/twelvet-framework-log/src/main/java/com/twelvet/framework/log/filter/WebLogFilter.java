package com.twelvet.framework.log.filter;


import com.twelvet.framework.core.constants.Constants;
import com.twelvet.framework.utils.DateUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@WebFilter(filterName = "BaseFilter", urlPatterns = "/*")
@Component
public class WebLogFilter implements Filter {
    private final static Logger log = LoggerFactory.getLogger(WebLogFilter.class);

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
        ResponseWrapper responseWrapper = new ResponseWrapper(servletResponse);

        // 健康检查
        if ("/actuator/health".equals(requestWrapper.getRequestURI())) {
            chain.doFilter(requestWrapper, responseWrapper);
            return;
        }

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
        response.getOutputStream().write(bytes);

        log.info(String.format(
                "\n===================Request================>\n时间：%s\n地址：%s\ntoken：%s\n参数：%s\n方式：%s"
                        + "\n<===================Response================\n状态：%s\n内容：%s\n时长：%s毫秒"
                        + "\n============================================",
                DateUtils.getTime(),
                requestWrapper.getRequestURL(),
                // 认证Token
                ServletUtils.getRequest().getHeader(Constants.AUTHORIZATION),
                reqJson,
                requestWrapper.getMethod(),
                responseWrapper.getStatus(),
                new String(bytes, StandardCharsets.UTF_8),
                endTime - startTime)
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
    public void init(FilterConfig filterConfig) {
        log.info("过滤器初始化");

    }

}
