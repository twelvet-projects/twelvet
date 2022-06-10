package com.twelvet.framework.security.config;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.utils.CharsetKit;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(ResourceAuthExceptionEntryPoint.class)''

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        try {
            response.setCharacterEncoding(CharsetKit.UTF_8);
            R<String> result = new R<>();
            result.setCode(CommonConstants.FAIL);
            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            if (authException != null) {
                result.setMsg("error");
                result.setData(authException.getMessage());
            }

            // 针对令牌过期返回特殊的 424
            if (authException instanceof InsufficientAuthenticationException) {
                int code = org.springframework.http.HttpStatus.FAILED_DEPENDENCY.value();
                result.setMsg("token expire");
            }
            ServletUtils.render(JacksonUtils.getInstance().writeValueAsString(result));
        } catch (Exception e) {

        }
    }

}