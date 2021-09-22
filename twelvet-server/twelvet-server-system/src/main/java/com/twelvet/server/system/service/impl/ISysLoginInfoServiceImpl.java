package com.twelvet.server.system.service.impl;

import com.twelvet.api.system.domain.SysLoginInfo;
import com.twelvet.server.system.mapper.SysLoginInfoMapper;
import com.twelvet.server.system.service.ISysLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 系统操作/访问日志
 */
@Service
public class ISysLoginInfoServiceImpl implements ISysLoginInfoService {

    @Autowired
    private SysLoginInfoMapper SysLoginInfoMapper;

    /**
     * 查询系统登录日志集合
     *
     * @param loginInfo 访问日志对象
     * @return List<SysLoginInfo>
     */
    @Override
    public List<SysLoginInfo> selectLoginInfoList(SysLoginInfo loginInfo) {
        return SysLoginInfoMapper.selectLoginInfoList(loginInfo);
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 操作结果
     */
    @Override
    public int deleteLoginInfoByIds(Long[] infoIds) {
        return SysLoginInfoMapper.deleteLoginInfoByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginInfo() {
        SysLoginInfoMapper.cleanLoginInfo();
    }

    /**
     * 新增系统登录日志
     *
     * @param loginInfo 访问日志对象
     */
    @Override
    public int insertLoginInfo(SysLoginInfo loginInfo) {
        return SysLoginInfoMapper.insertLoginInfo(loginInfo);
    }
}
