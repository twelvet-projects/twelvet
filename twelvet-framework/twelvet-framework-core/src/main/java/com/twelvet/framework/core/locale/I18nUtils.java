package com.twelvet.framework.core.locale;

import com.twelvet.framework.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

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
	 * @param msg 需要国际化的文字
	 * @return 返回国际化文字
	 */
	public static String getLocale(String msg) {
		try {
			return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
		}
		catch (Exception e) {
			log.error("获取国际化失败：{}", msg, e);
			return msg;
		}
	}

}
