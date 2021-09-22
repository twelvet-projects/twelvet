package com.twelvet.security.config;

import com.twelvet.framework.security.constans.CacheConstants;
import com.twelvet.framework.security.constans.SecurityConstants;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.service.RedisClientDetailsService;
import com.twelvet.security.granter.impl.PhonePasswordTokenGranter;
import com.twelvet.security.service.impl.TwTUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.*;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 认证服务配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TwTUserDetailsServiceImpl twTUserDetailsService;

    /**
     * 用户信息认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * Redis工厂
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 自定义用户信息
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 自定义认证失败处理
     */
    @Autowired
    private WebResponseExceptionTranslator<OAuth2Exception> customWebResponseExceptionTranslator;

    /**
     * 获取ResourceServer信息(使用Redis缓存信息)
     *
     * @return RedisClientDetailsService
     */
    @Bean
    public RedisClientDetailsService redisClientDetails() {
        return new RedisClientDetailsService(dataSource);
    }

    /**
     * Token储存(使用Redis实现)
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(CacheConstants.OAUTH_ACCESS);
        return tokenStore;
    }

    /**
     * 配置客户端信息
     *
     * @param clients clients
     * @throws Exception Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(redisClientDetails());
    }

    /**
     * 自定义扩展令牌信息
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            if (accessToken instanceof DefaultOAuth2AccessToken) {
                LoginUser user = (LoginUser) authentication.getUserAuthentication().getPrincipal();
                Map<String, Object> additionalInformation = new LinkedHashMap<>();
                additionalInformation.put("code", HttpStatus.OK.value());
                additionalInformation.put(SecurityConstants.DETAILS_DEPT_ID, user.getDeptId());
                additionalInformation.put(SecurityConstants.DETAILS_ROLES, user.getRoles());
                additionalInformation.put(SecurityConstants.DETAILS_USER_ID, user.getUserId());
                additionalInformation.put(SecurityConstants.DETAILS_USERNAME, user.getUsername());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
            }
            return accessToken;
        };
    }

    /**
     * 定义授权和令牌端点以及令牌服务
     *
     * @param endpoints endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

        List<TokenGranter> tokenGranters = getTokenGranters(
                endpoints.getAuthorizationCodeServices(),
                endpoints.getTokenServices(),
                endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory()
        );

        endpoints
                // 配置系统支持的授权模式
                .tokenGranter(new CompositeTokenGranter(tokenGranters))
                // 请求方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 指定token存储位置
                .tokenStore(tokenStore())
                // 自定义账号密码登录（Oauth2密码模式需要,刷新模式也需要）
                .userDetailsService(userDetailsService)
                // 指定认证管理器
                .authenticationManager(authenticationManager)
                // 是否重复使用 refresh_token
                .reuseRefreshTokens(false)
                // 增强access_token信息
                .tokenEnhancer(tokenEnhancer())
                // 自定义确认授权页面
                .pathMapping("/oauth/confirm_access", "/token/confirm_access")
                // 自定义异常处理
                .exceptionTranslator(customWebResponseExceptionTranslator);
    }

    /**
     * 配置Oauth2安全
     *
     * @param security AuthorizationServerSecurityConfigurer
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {

        security.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
    }

    /**
     * 配置系统支持的授权模式
     *
     * @param authorizationCodeServices AuthorizationCodeServices
     * @param tokenServices             TokenStore
     * @param clientDetailsService      AuthorizationServerTokenServices
     * @param requestFactory            OAuth2RequestFactory
     * @return List<TokenGranter>
     */
    private List<TokenGranter> getTokenGranters(AuthorizationCodeServices authorizationCodeServices, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        return new ArrayList<>(Arrays.asList(
                // 授权码模式
                new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory),
                // 客户端模式
                new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory),
                // 密码模式
                new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory),
                // 简化模式
                new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory),
                // 支持刷新模式
                new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory),
                // 自定义手机号密码模式例子(并未真正接入手机)
                new PhonePasswordTokenGranter(tokenServices, clientDetailsService, requestFactory, twTUserDetailsService)
        ));
    }

}
