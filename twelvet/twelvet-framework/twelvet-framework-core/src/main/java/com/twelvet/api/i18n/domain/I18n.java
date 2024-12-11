package com.twelvet.api.i18n.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 国际化对象 i18n
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-03-28
 */
@Schema(description = "国际化对象")
public class I18n extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Schema(description = "ID")
	private Long i18nId;

	/** 唯一Code */
	@Schema(description = "唯一Code")
	@ExcelProperty(value = "唯一Code")
	private String code;

	/** 语言类型：zh_CN,en... */
	@Schema(description = "语言类型：zh_CN,en_US...")
	@ExcelProperty(value = "语言类型：zh_CN,en_US...")
	private String type;

	/** 翻译值 */
	@Schema(description = "翻译值")
	@ExcelProperty(value = "翻译值")
	private String value;

	public void setI18nId(Long i18nId) {
		this.i18nId = i18nId;
	}

	public Long getI18nId() {
		return i18nId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("i18nId", getI18nId())
			.append("code", getCode())
			.append("type", getType())
			.append("value", getValue())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("remark", getRemark())
			.toString();
	}

}
