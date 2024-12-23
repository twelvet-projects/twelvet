package com.twelvet.server.ai.config;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.allow.WordAllows;
import com.github.houbb.sensitive.word.support.deny.WordDenys;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 敏感词配置
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-23
 */
@Configuration
public class SensitiveWordBsConfiguration {

	/**
	 * 敏感词检查
	 * @return SensitiveWordBs
	 */
	@Bean
	public SensitiveWordBs sensitiveWordBs() {
		return SensitiveWordBs.newInstance()
			// 使用默认的敏感词词库（黑名单）
			.wordDeny(WordDenys.defaults())
			// 使用默认的白名单词库，白名单中的词不会被视为敏感词，即使它们在黑名单中
			.wordAllow(WordAllows.defaults())
			// 忽略大小写，例如："FuCk" 和 "fuck" 将被同等对待
			.ignoreCase(Boolean.TRUE)
			// 忽略全角和半角字符的区别，例如："ｆｕｃｋ" 和 "fuck" 将被同等对待
			.ignoreWidth(Boolean.TRUE)
			// 启用连续数字检测， 可用于检测电话号码、QQ号等
			.enableNumCheck(Boolean.TRUE)
			// 启用邮箱地址检测，可用于过滤包含邮箱地址的文本
			.enableEmailCheck(Boolean.TRUE)
			// 初始化敏感词过滤器， 这一步必须在所有配置完成后调用
			.init();
	}

}
