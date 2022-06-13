package com.twelvet.framework.security.constans;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 权限相关常量
 */
public interface SecurityConstants {

    /**
     * {bcrypt} 加密的特征码
     */
    String BCRYPT = "{bcrypt}";

    /**
     * {noop} 加密的特征码
     */
    String NOOP = "{noop}";

    /**
     * 授权码模式confirm
     */
    String CUSTOM_CONSENT_PAGE_URI = "/token/confirm_access";

    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * 项目的license
     */
    String PROJECT_LICENSE = "https://www.twelvet.cn/docs/";

    /**
     * 协议字段
     */
    String DETAILS_LICENSE = "license";

    /**
     * 客户端ID
     */
    String CLIENT_ID = "clientId";

    /**
     * 用户信息
     */
    String DETAILS_USER = "user_info";

    /**
     * 手机号登录
     */
    String APP = "app";

    /**
     * 短信登录 参数名称
     */
    String SMS_PARAMETER_NAME = "mobile";
}
