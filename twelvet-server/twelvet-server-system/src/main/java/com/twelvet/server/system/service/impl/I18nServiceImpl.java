package com.twelvet.server.system.service.impl;

import com.twelvet.api.i18n.domain.I18n;
import com.twelvet.api.system.domain.SysDictData;
import com.twelvet.framework.core.locale.constants.LocaleCacheConstants;
import com.twelvet.framework.redis.service.RedisUtils;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.server.system.mapper.I18nMapper;
import com.twelvet.server.system.mapper.SysDictDataMapper;
import com.twelvet.server.system.service.II18nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 国际化Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-03-28
 */
@Service
public class I18nServiceImpl implements II18nService, ApplicationRunner {

	private final static Logger log = LoggerFactory.getLogger(I18nServiceImpl.class);

	@Autowired
	private I18nMapper i18nMapper;

	@Autowired
	private SysDictDataMapper dictDataMapper;

	/**
	 * 判断是否需要进行国际化初始化
	 * @param args ApplicationArguments
	 * @throws Exception Exception
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		initI18n(Boolean.FALSE);
	}

	/**
	 * 初始化国际化数据
	 * @param flushFlag 是否强制刷新数据
	 */
	@Override
	public void initI18n(Boolean flushFlag) {
		TUtils.threadPoolExecutor.execute(() -> {
			String hashFormat = String.format("%s::%s", LocaleCacheConstants.LOCALE, LocaleCacheConstants.ZH_CN);
			if (flushFlag || !RedisUtils.hasKey(hashFormat)) {
				log.info("Detected i18n deficiency, initializing");
				List<I18n> i18ns = i18nMapper.selectI18nList(new I18n());
				for (I18n i18n : i18ns) {
					String format = String.format("%s::%s:%s", LocaleCacheConstants.LOCALE, i18n.getType(),
							i18n.getCode());
					RedisUtils.setCacheObject(format, i18n.getValue());
				}
				// 完成初始化标志
				RedisUtils.setCacheObject(hashFormat, true);
				log.info("i18n initialization complete");
			}

			// 处理i18n支持语言缓存
			if (flushFlag || !RedisUtils.hasKey(LocaleCacheConstants.CACHE_DICT_CODE)) {
				List<SysDictData> sysDictData = dictDataMapper.selectDictDataByType(LocaleCacheConstants.DICT_CODE);
				RedisUtils.setCacheObject(LocaleCacheConstants.CACHE_DICT_CODE, sysDictData);
			}
		});
	}

	/**
	 * 查询国际化
	 * @param i18nId 国际化主键
	 * @return 国际化
	 */
	@Override
	public I18n selectI18nByI18nId(Long i18nId) {
		return i18nMapper.selectI18nByI18nId(i18nId);
	}

	/**
	 * 查询国际化列表
	 * @param i18n 国际化
	 * @return 国际化
	 */
	@Override
	public List<I18n> selectI18nList(I18n i18n) {
		return i18nMapper.selectI18nList(i18n);
	}

	/**
	 * 新增国际化
	 * @param i18n 国际化
	 * @return 结果
	 */
	@Override
	public int insertI18n(I18n i18n) {
		LocalDateTime nowDate = LocalDateTime.now();
		i18n.setCreateTime(nowDate);
		i18n.setUpdateTime(nowDate);
		String loginUsername = SecurityUtils.getUsername();
		i18n.setCreateBy(loginUsername);
		i18n.setUpdateBy(loginUsername);
		int insertCount = i18nMapper.insertI18n(i18n);
		String format = String.format("%s::%s:%s", LocaleCacheConstants.LOCALE, i18n.getType(), i18n.getCode());
		RedisUtils.setCacheObject(format, i18n.getValue());
		return insertCount;
	}

	/**
	 * 修改国际化
	 * @param i18n 国际化
	 * @return 结果
	 */
	@Override
	public int updateI18n(I18n i18n) {
		i18n.setUpdateTime(LocalDateTime.now());
		String loginUsername = SecurityUtils.getUsername();
		i18n.setCreateBy(loginUsername);
		i18n.setUpdateBy(loginUsername);
		int updateCount = i18nMapper.updateI18n(i18n);

		String format = String.format("%s::%s:%s", LocaleCacheConstants.LOCALE, i18n.getType(), i18n.getCode());
		RedisUtils.setCacheObject(format, i18n.getValue());

		return updateCount;
	}

	/**
	 * 批量删除国际化
	 * @param i18nIds 需要删除的国际化主键
	 * @return 结果
	 */
	@Override
	public int deleteI18nByI18nIds(Long[] i18nIds) {
		int deleteCount = i18nMapper.deleteI18nByI18nIds(i18nIds);
		for (Long i18nId : i18nIds) {
			I18n i18n = i18nMapper.selectI18nByI18nId(i18nId);

			String format = String.format("%s::%s:%s", LocaleCacheConstants.LOCALE, i18n.getType(), i18n.getCode());
			RedisUtils.deleteObject(format);
		}
		return deleteCount;
	}

	/**
	 * 删除国际化信息
	 * @param i18nId 国际化主键
	 * @return 结果
	 */
	@Override
	public int deleteI18nByI18nId(Long i18nId) {
		I18n i18n = i18nMapper.selectI18nByI18nId(i18nId);

		String format = String.format("%s::%s:%s", LocaleCacheConstants.LOCALE, i18n.getType(), i18n.getCode());
		RedisUtils.deleteObject(format);
		return i18nMapper.deleteI18nByI18nId(i18nId);
	}

}
