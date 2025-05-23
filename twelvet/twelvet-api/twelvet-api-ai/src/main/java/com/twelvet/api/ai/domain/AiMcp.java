package com.twelvet.api.ai.domain;

import cn.idev.excel.annotation.ExcelProperty;
import com.twelvet.api.ai.constant.ModelEnums;
import com.twelvet.framework.core.application.domain.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;

/**
 * AI MCP服务对象 ai_mcp
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-03-31
 */
@Schema(description = "AI MCP服务对象")
public class AiMcp extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * MCP ID
	 */
	@Schema(description = "MCP ID")
	private Long mcpId;

	/**
	 * MCP服务名称
	 */
	@NotBlank(message = "MCP服务名称不能为空")
	@Schema(description = "MCP服务名称")
	@ExcelProperty(value = "MCP服务名称")
	private String name;

	/**
	 * 描述
	 */
	@Schema(description = "描述")
	@ExcelProperty(value = "描述")
	private String description;

	/**
	 * 请求类型
	 */
	@NotNull(message = "请求类型不能为空")
	@Schema(description = "请求类型")
	@ExcelProperty(value = "请求类型")
	private ModelEnums.McpTypeEnums mcpType;

	/**
	 * 命令
	 */
	@Schema(description = "命令")
	@ExcelProperty(value = "命令")
	private ModelEnums.McpCommandEnums command;

	/**
	 * SSE请求基础地址
	 */
	@Schema(description = "SSE请求基础地址")
	@ExcelProperty(value = "SSE请求基础地址")
	private String sseBaseUrl;

	/**
	 * SSE请求端点
	 */
	@Schema(description = "SSE请求端点")
	@ExcelProperty(value = "SSE请求端点")
	private String sseEndpoint;

	/**
	 * 参数
	 */
	@Schema(description = "参数")
	@ExcelProperty(value = "参数")
	private String args;

	/**
	 * 环境变量
	 */
	@Schema(description = "环境变量")
	@ExcelProperty(value = "环境变量")
	private String env;

	/**
	 * 是否启用 1：启用，0：关闭
	 */
	@Schema(description = "是否启用  1：启用，0：关闭")
	@ExcelProperty(value = "是否启用  1：启用，0：关闭")
	private Boolean statusFlag;

	/**
	 * 是否删除 0：正常，1：删除
	 */
	@Schema(description = "是否删除 0：正常，1：删除")
	private Boolean delFlag;

	public Long getMcpId() {
		return mcpId;
	}

	public void setMcpId(Long mcpId) {
		this.mcpId = mcpId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ModelEnums.McpTypeEnums getMcpType() {
		return mcpType;
	}

	public void setMcpType(ModelEnums.McpTypeEnums mcpType) {
		this.mcpType = mcpType;
	}

	public ModelEnums.McpCommandEnums getCommand() {
		return command;
	}

	public void setCommand(ModelEnums.McpCommandEnums command) {
		this.command = command;
	}

	public String getSseBaseUrl() {
		return sseBaseUrl;
	}

	public void setSseBaseUrl(String sseBaseUrl) {
		this.sseBaseUrl = sseBaseUrl;
	}

	public String getSseEndpoint() {
		return sseEndpoint;
	}

	public void setSseEndpoint(String sseEndpoint) {
		this.sseEndpoint = sseEndpoint;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public Boolean getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Boolean statusFlag) {
		this.statusFlag = statusFlag;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("mcpId", getMcpId())
			.append("name", getName())
			.append("description", getDescription())
			.append("mcpType", getMcpType())
			.append("sseBaseUrl", getSseBaseUrl())
			.append("sseEndpoint", getSseEndpoint())
			.append("command", getCommand())
			.append("args", getArgs())
			.append("env", getEnv())
			.append("statusFlag", getStatusFlag())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("delFlag", getDelFlag())
			.toString();
	}

}
