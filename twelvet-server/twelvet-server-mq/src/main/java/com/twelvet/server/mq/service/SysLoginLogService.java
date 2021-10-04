package com.twelvet.server.mq.service;

import com.twelvet.api.system.domain.SysLoginInfo;

/**
 * @author twelvet
 * <p>
 * 系统登录日志业务层
 */
public interface SysLoginLogService {

    /**
     * 发送系统登录日志MQ
     *
     * @param sysLoginInfo SysLoginInfo
     */
    void sendSysLoginLog(SysLoginInfo sysLoginInfo);

}
