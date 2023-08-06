package com.twelvet.api.job.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.twelvet.framework.core.application.domain.BaseEntity;
import com.twelvet.framework.core.constants.ScheduleConstants;
import com.twelvet.framework.utils.CronUtils;
import com.twelvet.framework.utils.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.util.Date;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 定时任务调度表 sys_job
 */
@Schema(description = "定时任务调度表")
public class SysJob extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 任务ID
	 */
	@Schema(description = "任务序号")
	@ExcelProperty(value = "任务序号")
	private Long jobId;

	/**
	 * 任务名称
	 */
	@Schema(description = "任务名称")
	@ExcelProperty(value = "任务名称")
	private String jobName;

	/**
	 * 任务组名
	 */
	@Schema(description = "任务组名")
	@ExcelProperty(value = "任务组名")
	private String jobGroup;

	/**
	 * 调用目标字符串
	 */
	@Schema(description = "调用目标字符串")
	@ExcelProperty(value = "调用目标字符串")
	private String invokeTarget;

	/**
	 * cron执行表达式
	 */
	@Schema(description = "执行表达式")
	@ExcelProperty(value = "执行表达式 ")
	private String cronExpression;

	/**
	 * cron计划策略
	 */
	@Schema(description = "计划策略")
	@ExcelProperty(value = "计划策略(0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行)")
	private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

	/**
	 * 是否并发执行（0允许 1禁止）
	 */
	@Schema(description = "是否并发执行")
	@ExcelProperty(value = "并发执行(0=允许,1=禁止)")
	private String concurrent;

	/**
	 * 任务状态（0正常 1暂停）
	 */
	@Schema(description = "任务状态")
	@ExcelProperty(value = "任务状态(0=正常,1=暂停)")
	private String status;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	@NotBlank(message = "任务名称不能为空")
	@Size(min = 0, max = 64, message = "任务名称不能超过64个字符")
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	@NotBlank(message = "调用目标字符串不能为空")
	@Size(min = 0, max = 500, message = "调用目标字符串长度不能超过500个字符")
	public String getInvokeTarget() {
		return invokeTarget;
	}

	public void setInvokeTarget(String invokeTarget) {
		this.invokeTarget = invokeTarget;
	}

	@NotBlank(message = "Cron执行表达式不能为空")
	@Size(min = 0, max = 255, message = "Cron执行表达式不能超过255个字符")
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNextValidTime() {
		if (StringUtils.isNotEmpty(cronExpression)) {
			return CronUtils.getNextExecution(cronExpression);
		}
		return null;
	}

	public String getMisfirePolicy() {
		return misfirePolicy;
	}

	public void setMisfirePolicy(String misfirePolicy) {
		this.misfirePolicy = misfirePolicy;
	}

	public String getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("jobId", getJobId())
			.append("jobName", getJobName())
			.append("jobGroup", getJobGroup())
			.append("cronExpression", getCronExpression())
			.append("nextValidTime", getNextValidTime())
			.append("misfirePolicy", getMisfirePolicy())
			.append("concurrent", getConcurrent())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("remark", getRemark())
			.toString();
	}

}
