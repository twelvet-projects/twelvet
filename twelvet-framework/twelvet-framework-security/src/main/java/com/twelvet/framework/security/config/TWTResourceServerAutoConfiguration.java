package com.twelvet.framework.security.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

@Configuration
@EnableConfigurationProperties(AuthIgnoreConfig.class)
public class TWTResourceServerAutoConfiguration {

    /**
     * 请求令牌的抽取逻辑
     *
     * @param urlProperties 对外暴露的接口列表
     * @return BearerTokenExtractor
     */
    @Bean
    public TWTBearerTokenExtractor twtBearerTokenExtractor(AuthIgnoreConfig urlProperties) {
        return new TWTBearerTokenExtractor(urlProperties);
    }

    /**
     * 资源服务器异常处理
     *
     * @return ResourceAuthExceptionEntryPoint
     */
    @Bean
    public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint() {
        return new ResourceAuthExceptionEntryPoint();
    }

    /**
     * 资源服务器toke内省处理器
     *
     * @return TokenIntrospector
     */
    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService oAuth2AuthorizationService) {
        return new TWTCustomOpaqueTokenIntrospect(oAuth2AuthorizationService);
    }

}