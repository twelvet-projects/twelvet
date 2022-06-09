package com.twelvet.framework.security.constans;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 权限相关常量
 */
public interface SecurityConstants {

    /**
     * 授权token url
     */
    String AUTH_TOKEN = "/oauth/token";

    /**
     * 注销token url
     */
    String TOKEN_LOGOUT = "/token/logout";

    /**
     * 用户ID字段
     */
    String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段
     */
    String DETAILS_USERNAME = "username";

    /**
     * 部门ID
     */
    String DETAILS_DEPT_ID = "dept_id";

    /**
     * 权限组
     */
    String DETAILS_ROLES = "roles";

    /**
     * {bcrypt} 加密的特征码
     */
    String BCRYPT = "{bcrypt}";

    /**
     * {noop} 加密的特征码
     */
    String NOOP = "{noop}";

}
