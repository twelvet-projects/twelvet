package com.twelvet.framework.core.locale;

import com.twelvet.framework.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;

import java.util.Locale;

/**
 * 国际化工具类
 *
 * @author twelvet
 */
public class I18nUtils {

	private final static Logger log = LoggerFactory.getLogger(I18nUtils.class);

	private I18nUtils() {
		throw new IllegalStateException("Utility class");
	}

	private static final MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);

	/**
	 * 获取当前国际化文字
	 * @param code 需要国际化的文字
	 * @return 返回国际化文字
	 */
	public static String getLocale(String code) {
		try {
			return getLocale(code, null, code, LocaleContextHolder.getLocale());
		}
		catch (Exception e) {
			log.error("获取国际化失败：{}", code, e);
			return code;
		}
	}

	/**
	 * 获取当前国际化文字
	 * @param code 需要国际化的文字
	 * @param args 动态参数
	 * @return 返回国际化文字
	 */
	public static String getLocale(String code, @Nullable Object[] args) {
		try {
			return getLocale(code, args, code, LocaleContextHolder.getLocale());
		}
		catch (Exception e) {
			log.error("获取国际化失败：{}", code, e);
			return code;
		}
	}

	/**
	 * 获取当前国际化文字
	 * @param code 需要国际化的文字
	 * @param defaultMessage 默认数据
	 * @return 返回国际化文字
	 */
	public static String getLocale(String code, @Nullable String defaultMessage) {
		try {
			return getLocale(code, null, defaultMessage, LocaleContextHolder.getLocale());
		}
		catch (Exception e) {
			log.error("获取国际化失败：{}", code, e);
			return code;
		}
	}

	/**
	 * 获取当前国际化文字
	 * @param code 需要国际化的文字
	 * @param args 动态参数
	 * @param defaultMessage 默认参数
	 * @return 返回国际化文字
	 */
	public static String getLocale(String code, @Nullable Object[] args, @Nullable String defaultMessage) {
		try {
			return getLocale(code, args, defaultMessage, LocaleContextHolder.getLocale());
		}
		catch (Exception e) {
			log.error("获取国际化失败：{}", code, e);
			return code;
		}
	}

	/**
	 * 获取当前国际化文字
	 * @param code 需要国际化的文字
	 * @param args 动态参数
	 * @param locale 使用语言
	 * @return 返回国际化文字
	 */
	public static String getLocale(String code, @Nullable Object[] args, Locale locale) {
		try {
			return getLocale(code, args, code, locale);
		}
		catch (Exception e) {
			log.error("获取国际化失败：{}", code, e);
			return code;
		}
	}

	/**
	 * 获取当前国际化文字
	 * @param code 需要国际化的文字
	 * @param args 动态参数
	 * @param defaultMessage 默认数据
	 * @param locale 使用语言
	 * @return 返回国际化文字
	 */
	public static String getLocale(String code, @Nullable Object[] args, @Nullable String defaultMessage,
			Locale locale) {
		try {
			return messageSource.getMessage(code, args, defaultMessage, locale);
		}
		catch (Exception e) {
			log.error("获取国际化失败：{}", code, e);
			return code;
		}
	}

}
