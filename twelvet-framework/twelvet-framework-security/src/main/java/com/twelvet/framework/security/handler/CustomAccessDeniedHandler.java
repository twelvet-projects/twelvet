package com.twelvet.framework.security.handler;


import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 自定义无权限时异常响应
 */
@Component
@EnableAutoConfiguration
public class CustomAccessDeniedHandler extends OAuth2AccessDeniedHandler {
    private final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException {
        logger.info("权限不足，请联系管理员 {}", request.getRequestURI());

        int code = HttpStatus.FORBIDDEN.value();

        ServletUtils.render(
                code,
                JacksonUtils.toJson(AjaxResult.error(code, authException.getMessage()))
        );
    }
}