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
 * @Description: 岗位表 sys_post
 */
@Schema(description = "岗位表")
public class SysPost extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 岗位序号
	 */
	@Schema(description = "岗位序号")
	@ExcelProperty(value = "岗位序号")
	private Long postId;

	/**
	 * 岗位编码
	 */
	@Schema(description = "岗位编码")
	@ExcelProperty(value = "岗位编码")
	private String postCode;

	/**
	 * 岗位名称
	 */
	@Schema(description = "岗位名称")
	@ExcelProperty(value = "岗位名称")
	private String postName;

	/**
	 * 岗位排序
	 */
	@Schema(description = "岗位排序")
	@ExcelProperty(value = "岗位排序")
	private String postSort;

	/**
	 * 状态（0正常 1停用）
	 */
	@Schema(description = "状态")
	@ExcelProperty(value = "状态(0=正常,1=停用)")
	private String status;

	/**
	 * 用户是否存在此岗位标识 默认不存在
	 */
	@Schema(description = "岗位标识")
	private boolean flag = false;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	@NotBlank(message = "岗位编码不能为空")
	@Size(min = 0, max = 64, message = "岗位编码长度不能超过64个字符")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@NotBlank(message = "岗位名称不能为空")
	@Size(min = 0, max = 50, message = "岗位名称长度不能超过50个字符")
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@NotBlank(message = "显示顺序不能为空")
	public String getPostSort() {
		return postSort;
	}

	public void setPostSort(String postSort) {
		this.postSort = postSort;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("postId", getPostId())
			.append("postCode", getPostCode())
			.append("postName", getPostName())
			.append("postSort", getPostSort())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("remark", getRemark())
			.toString();
	}

}
