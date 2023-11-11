package com.twelvet.api.gen.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.twelvet.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;

/**
 * 数据源对象 gen_datasource_conf
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 */
@Schema(description = "数据源对象")
public class GenDatasourceConf extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Schema(description = "主键")
	private Long id;

	/** 别名 */
	@Schema(description = "别名")
	@ExcelProperty(value = "别名")
	private String name;

	/** jdbcurl */
	@Schema(description = "jdbcurl")
	@ExcelProperty(value = "jdbcurl")
	private String url;

	/** 用户名 */
	@Schema(description = "用户名")
	@ExcelProperty(value = "用户名")
	private String username;

	/** 密码 */
	@Schema(description = "密码")
	@ExcelProperty(value = "密码")
	private String password;

	/** 删除标记 */
	@Schema(description = "删除标记")
	private String delFlag;

	/** 数据库类型 */
	@Schema(description = "数据库类型")
	@ExcelProperty(value = "数据库类型")
	private String dsType;

	/** 配置类型(0：主机模式，1：JDBC) */
	@Schema(description = "配置类型(0：主机模式，1：JDBC)")
	@ExcelProperty(value = "配置类型(0：主机模式，1：JDBC)")
	private String confType;

	/** 数据库名称 */
	@Schema(description = "数据库名称")
	@ExcelProperty(value = "数据库名称")
	private String dsName;

	/** 实例 */
	@Schema(description = "实例")
	@ExcelProperty(value = "实例")
	private String instance;

	/** 端口 */
	@Schema(description = "端口")
	@ExcelProperty(value = "端口")
	private Long port;

	/** 主机 */
	@Schema(description = "主机")
	@ExcelProperty(value = "主机")
	private String host;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	public String getDsType() {
		return dsType;
	}

	public void setConfType(String confType) {
		this.confType = confType;
	}

	public String getConfType() {
		return confType;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getDsName() {
		return dsName;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getInstance() {
		return instance;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	public Long getPort() {
		return port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
			.append("name", getName())
			.append("url", getUrl())
			.append("username", getUsername())
			.append("password", getPassword())
			.append("createTime", getCreateTime())
			.append("updateTime", getUpdateTime())
			.append("delFlag", getDelFlag())
			.append("dsType", getDsType())
			.append("confType", getConfType())
			.append("dsName", getDsName())
			.append("instance", getInstance())
			.append("port", getPort())
			.append("host", getHost())
			.toString();
	}

}
