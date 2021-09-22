package com.twelvet.framework.security.config;

import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.framework.security.config.properties.IgnoreUrlsProperties;
import com.twelvet.framework.utils.TWTUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 忽略服务间的认证
 */
@Configurable
public class AuthIgnoreConfig implements InitializingBean {
    /**
     * 注解urls
     */
    @Autowired
    private IgnoreUrlsProperties ignoreUrlsProperties;

    @Autowired
    private WebApplicationContext applicationContext;

    private List<String> ignoreUrls = new ArrayList<>();

    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    /**
     * 重写bean注入后
     */
    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        // 遍历所有mapping
        map.keySet().forEach(mappingInfo -> {

            HandlerMethod handlerMethod = map.get(mappingInfo);
            // 检测方法是否存在注解
            AuthIgnore method = handlerMethod.getMethod().getAnnotation(AuthIgnore.class);
            // 检测Controller是否在注解
            AuthIgnore controller = handlerMethod.getBeanType().getAnnotation(AuthIgnore.class);

            // 本方法或本Controller存在AuthIgnore注解将存进列表
            if (TWTUtils.isNotEmpty(method) || TWTUtils.isNotEmpty(controller)) {
                ignoreUrls.addAll(mappingInfo.getPatternsCondition().getPatterns());
            }

        });

        // 合并放行路径
        ignoreUrls.addAll(ignoreUrlsProperties.getUrls());
    }
}
