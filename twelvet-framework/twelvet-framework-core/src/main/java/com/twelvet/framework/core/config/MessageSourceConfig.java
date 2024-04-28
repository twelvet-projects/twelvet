package com.twelvet.framework.core.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.twelvet.api.system.domain.SysDictData;
import com.twelvet.framework.core.config.properties.MessageSourceProperties;
import com.twelvet.framework.core.locale.constants.LocaleCacheConstants;
import com.twelvet.framework.redis.service.RedisUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.TUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.AbstractMessageSource;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 缓存读取国际化信息
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Primary
public class MessageSourceConfig extends AbstractMessageSource implements ApplicationRunner {

	@Autowired
	private MessageSourceProperties messageSourceProperties;

	/**
	 * Caffeine缓存实例
	 */
	private Cache<String, Object> cache;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		cache = Caffeine.newBuilder()
			.initialCapacity(1000) // 初始容量
			.maximumSize(30000) // 最大容量
			.expireAfterWrite(messageSourceProperties.getCacheDuration(), TimeUnit.SECONDS) // 写入后过期时间
			.build();
	}

	/**
	 * 创建国际化
	 * @param msg code
	 * @param locale 语言
	 * @return 国际化信息
	 */
	@NotNull
	@Override
	protected MessageFormat createMessageFormat(@NotNull String msg, @NotNull Locale locale) {
		return new MessageFormat(msg, locale);
	}

	/**
	 * 工具应用
	 * @param code the code to lookup up, such as 'calculator.noRateSet'
	 * @param args array of arguments that will be filled in for params within the message
	 * @param locale the locale in which to do the lookup
	 * @return String
	 */
	@Override
	protected String getMessageInternal(String code, Object[] args, Locale locale) {

		// 三级缓存
		List<SysDictData> localeCacheList = (List<SysDictData>) cache
			.getIfPresent(LocaleCacheConstants.CACHE_DICT_CODE);
		if (TUtils.isEmpty(localeCacheList)) {
			localeCacheList = RedisUtils.getCacheObject(LocaleCacheConstants.CACHE_DICT_CODE);

			cache.put(LocaleCacheConstants.CACHE_DICT_CODE, localeCacheList);
		}
		// 检测并使用默认语言
		Locale useLocale = null;
		if (localeCacheList != null) {
			for (SysDictData localeCache : localeCacheList) {
				String dictValue = localeCache.getDictValue();
				if (localeCache.getDictValue().equalsIgnoreCase(locale.toString())) {
					String[] dictValues = dictValue.split("_");
					useLocale = new Locale(dictValues[0], dictValues[1]);
					break;
				}
			}
		}
		if (TUtils.isEmpty(useLocale)) {
			// 默认中文
			String[] dictValues = LocaleCacheConstants.ZH_CN.split("_");
			useLocale = new Locale(dictValues[0], dictValues[1]);
		}

		String format = String.format("%s:%s:%s", LocaleCacheConstants.LOCALE, useLocale.toString(), code);
		String cacheMessage = (String) cache.getIfPresent(format);
		if (StringUtils.isNotEmpty(cacheMessage)) {
			return cacheMessage;
		}

		// 优先从静态资源获取(一般建议静态国际化优先使用，需要动态国际化再考虑数据库化)
		String basenames = messageSourceProperties.getBasename();
		String[] basenameList = basenames.split(",");
		String message = "";
		for (String basename : basenameList) {
			try {
				if (StringUtils.isNotEmpty(message)) {
					break;
				}
				message = ResourceBundle.getBundle(basename, useLocale).getString(code);
			}
			catch (Exception ignored) {

			}
		}

		if (StringUtils.isEmpty(message)) {
			// 从Redis缓存获取
			message = getMessageFromDatabase(code, useLocale);
		}

		// 进行本地缓存
		if (!message.equals(code) && TUtils.isNotEmpty(args) && args.length > 0) {
			message = createMessageFormat(message, useLocale).format(args);
			cache.put(format, message);
		}

		return message;

	}

	/**
	 * 模板应用
	 * @param code the code of the message to resolve
	 * @param locale the locale to resolve the code for (subclasses are encouraged to
	 * support internationalization)
	 * @return MessageFormat
	 */
	@Override
	protected MessageFormat resolveCode(@NotNull String code, @NotNull Locale locale) {
		String message = getMessageFromDatabase(code, locale);
		return createMessageFormat(Objects.requireNonNullElse(message, code), locale);
	}

	/**
	 * 自定义获取
	 * @param code 唯一key
	 * @param locale 获取语言
	 * @return 指定信息
	 */
	private String getMessageFromDatabase(String code, Locale locale) {

		String format = String.format("%s::%s:%s", LocaleCacheConstants.LOCALE, locale.toString(), code);
		String message = RedisUtils.getCacheObject(format);
		if (StringUtils.isEmpty(message)) {
			message = code;
		}
		return message;
	}

}
