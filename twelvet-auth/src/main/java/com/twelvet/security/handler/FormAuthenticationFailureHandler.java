package com.twelvet.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.twelvet.framework.utils.http.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 登录失败处理
 */
public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final static Logger log = LoggerFactory.getLogger(FormAuthenticationFailureHandler.class);

    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) {
        log.debug("表单登录失败:{}", exception.getLocalizedMessage());
        String url = HttpUtil.encodeParams(String.format("/token/login?error=%s", exception.getMessage()),
                CharsetUtil.CHARSET_UTF_8);
        try {
            ServletUtils.getResponse().sendRedirect(url);
        } catch (IOException e) {
            log.error("响应失败", e);
        }
    }

}