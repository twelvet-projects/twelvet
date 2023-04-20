package com.twelvet.server.system.service;

import com.twelvet.api.system.domain.SysLoginInfo;

import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 系统操作/访问日志
 */
public interface ISysLoginInfoService {

	/**
	 * 查询系统登录日志集合
	 * @param loginInfo 访问日志对象
	 * @return 登录记录集合
	 */
	List<SysLoginInfo> selectLoginInfoList(SysLoginInfo loginInfo);

	/**
	 * 批量删除系统登录日志
	 * @param infoIds 需要删除的登录日志ID
	 * @return
	 */
	int deleteLoginInfoByIds(Long[] infoIds);

	/**
	 * 清空系统登录日志
	 */
	void cleanLoginInfo();

	/**
	 * 新增系统登录日志
	 * @param loginInfo 访问日志对象
	 * @return 插入ID
	 */
	int insertLoginInfo(SysLoginInfo loginInfo);

}
