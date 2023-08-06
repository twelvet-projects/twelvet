package com.twelvet.api.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 参数配置表 sys_config
 */
@Schema(description = "参数配置表")
public class SysConfig extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 参数主键
	 */
	@Schema(description = "参数主键")
	@ExcelProperty(value = "参数主键")
	private Long configId;

	/**
	 * 参数名称
	 */
	@Schema(description = "参数名称")
	@ExcelProperty(value = "参数名称")
	private String configName;

	/**
	 * 参数键名
	 */
	@Schema(description = "参数键名")
	@ExcelProperty(value = "参数键名")
	private String configKey;

	/**
	 * 参数键值
	 */
	@Schema(description = "参数键值")
	@ExcelProperty(value = "参数键值")
	private String configValue;

	/**
	 * 系统内置（Y是 N否）
	 */
	@Schema(description = "系统内置")
	@ExcelProperty(value = "系统内置(Y=是,N=否)")
	private String configType;

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	@NotBlank(message = "参数名称不能为空")
	@Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	@NotBlank(message = "参数键名长度不能为空")
	@Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	@NotBlank(message = "参数键值不能为空")
	@Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("configId", getConfigId())
			.append("configName", getConfigName())
			.append("configKey", getConfigKey())
			.append("configValue", getConfigValue())
			.append("configType", getConfigType())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("remark", getRemark())
			.toString();
	}

}
