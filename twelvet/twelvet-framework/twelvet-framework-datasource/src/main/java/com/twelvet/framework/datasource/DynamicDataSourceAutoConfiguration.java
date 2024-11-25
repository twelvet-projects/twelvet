package com.twelvet.framework.datasource;

import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.hikaricp.HikariDataSourceCreator;
import com.baomidou.dynamic.datasource.processor.DsJakartaHeaderProcessor;
import com.baomidou.dynamic.datasource.processor.DsJakartaSessionProcessor;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.processor.DsSpelExpressionProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.twelvet.framework.datasource.config.ClearTtlDataSourceFilter;
import com.twelvet.framework.datasource.config.JdbcDynamicDataSourceProvider;
import com.twelvet.framework.datasource.config.LastParamDsProcessor;
import com.twelvet.framework.datasource.config.properties.DataSourceProperties;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 动态数据源切换配置
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {

	/**
	 * 动态数据源提供者
	 * @param defaultDataSourceCreator 默认数据源创建器
	 * @param stringEncryptor 字符串加密器
	 * @param properties 数据源配置属性
	 * @return 动态数据源提供者
	 */
	@Bean
	public DynamicDataSourceProvider dynamicDataSourceProvider(DefaultDataSourceCreator defaultDataSourceCreator,
			StringEncryptor stringEncryptor, DataSourceProperties properties) {
		return new JdbcDynamicDataSourceProvider(defaultDataSourceCreator, stringEncryptor, properties);
	}

	/**
	 * 获取数据源处理器
	 * @return 数据源处理器
	 */
	@Bean
	public DsProcessor dsProcessor(BeanFactory beanFactory) {
		DsProcessor lastParamDsProcessor = new LastParamDsProcessor();
		DsProcessor headerProcessor = new DsJakartaHeaderProcessor();
		DsProcessor sessionProcessor = new DsJakartaSessionProcessor();
		DsSpelExpressionProcessor spelExpressionProcessor = new DsSpelExpressionProcessor();
		spelExpressionProcessor.setBeanResolver(new BeanFactoryResolver(beanFactory));
		lastParamDsProcessor.setNextProcessor(headerProcessor);
		headerProcessor.setNextProcessor(sessionProcessor);
		sessionProcessor.setNextProcessor(spelExpressionProcessor);
		return lastParamDsProcessor;
	}

	/**
	 * 默认数据源创建器
	 * @param hikariDataSourceCreator Hikari数据源创建器
	 * @return 默认数据源创建器
	 */
	@Bean
	public DefaultDataSourceCreator defaultDataSourceCreator(HikariDataSourceCreator hikariDataSourceCreator) {
		DefaultDataSourceCreator defaultDataSourceCreator = new DefaultDataSourceCreator();
		List<DataSourceCreator> creators = new ArrayList<>();
		creators.add(hikariDataSourceCreator);
		defaultDataSourceCreator.setCreators(creators);
		return defaultDataSourceCreator;
	}

	/**
	 * 清除Ttl数据源过滤器
	 * @return 清除Ttl数据源过滤器
	 */
	@Bean
	public ClearTtlDataSourceFilter clearTtlDsFilter() {
		return new ClearTtlDataSourceFilter();
	}

}
