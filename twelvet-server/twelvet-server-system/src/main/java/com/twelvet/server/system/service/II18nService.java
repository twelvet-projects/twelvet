package com.twelvet.server.system.service;

import java.util.List;

import com.twelvet.api.i18n.domain.I18n;

/**
 * 国际化Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-03-28
 */
public interface II18nService {

	/**
	 * 初始化国际化数据
	 * @param flushFlag 是否强制刷新数据
	 */
	public void initI18n(Boolean flushFlag);

	/**
	 * 查询国际化
	 * @param i18nId 国际化主键
	 * @return 国际化
	 */
	public I18n selectI18nByI18nId(Long i18nId);

	/**
	 * 查询国际化列表
	 * @param i18n 国际化
	 * @return 国际化集合
	 */
	public List<I18n> selectI18nList(I18n i18n);

	/**
	 * 新增国际化
	 * @param i18n 国际化
	 * @return 结果
	 */
	public int insertI18n(I18n i18n);

	/**
	 * 修改国际化
	 * @param i18n 国际化
	 * @return 结果
	 */
	public int updateI18n(I18n i18n);

	/**
	 * 批量删除国际化
	 * @param i18nIds 需要删除的国际化主键集合
	 * @return 结果
	 */
	public int deleteI18nByI18nIds(Long[] i18nIds);

	/**
	 * 删除国际化信息
	 * @param i18nId 国际化主键
	 * @return 结果
	 */
	public int deleteI18nByI18nId(Long i18nId);

}
