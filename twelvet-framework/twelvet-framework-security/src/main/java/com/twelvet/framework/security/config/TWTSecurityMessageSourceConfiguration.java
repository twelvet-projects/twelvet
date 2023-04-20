package com.twelvet.framework.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

import java.util.Locale;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 配置国际化
 */
@ConditionalOnWebApplication(type = SERVLET)
public class TWTSecurityMessageSourceConfiguration implements WebMvcConfigurer {

	@Bean
	public MessageSource securityMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.addBasenames("classpath:i18n/errors/messages");
		messageSource.setDefaultLocale(Locale.CHINA);
		return messageSource;
	}

}
