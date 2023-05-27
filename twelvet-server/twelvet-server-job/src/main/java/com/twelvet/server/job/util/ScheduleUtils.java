package com.twelvet.server.job.util;

import com.twelvet.api.job.domain.SysJob;
import com.twelvet.framework.core.constants.Constants;
import com.twelvet.framework.core.constants.ScheduleConstants;
import com.twelvet.framework.utils.SpringContextHolder;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.server.job.exception.TaskException;
import com.twelvet.server.job.exception.TaskException.Code;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 定时任务工具类
 */
public class ScheduleUtils {

	/**
	 * 得到quartz任务类
	 * @param sysJob 执行计划
	 * @return 具体执行任务类
	 */
	private static Class<? extends Job> getQuartzJobClass(SysJob sysJob) {
		boolean isConcurrent = "0".equals(sysJob.getConcurrent());
		return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
	}

	/**
	 * 构建任务触发对象
	 */
	public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
		return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
	}

	/**
	 * 构建任务键对象
	 */
	public static JobKey getJobKey(Long jobId, String jobGroup) {
		return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
	}

	/**
	 * 创建定时任务
	 */
	public static void createScheduleJob(Scheduler scheduler, SysJob job) throws SchedulerException, TaskException {
		Class<? extends Job> jobClass = getQuartzJobClass(job);
		// 构建job信息
		Long jobId = job.getJobId();
		String jobGroup = job.getJobGroup();
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

		// 表达式调度构建器
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
		cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger()
			.withIdentity(getTriggerKey(jobId, jobGroup))
			.withSchedule(cronScheduleBuilder)
			.build();

		// 放入参数，运行时的方法可以获取
		jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

		// 判断是否存在
		if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
			// 防止创建时存在数据问题 先移除，然后在执行创建操作
			scheduler.deleteJob(getJobKey(jobId, jobGroup));
		}

		scheduler.scheduleJob(jobDetail, trigger);

		// 暂停任务
		if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
			scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
		}
	}

	/**
	 * 设置定时任务策略
	 */
	public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJob job, CronScheduleBuilder cb)
			throws TaskException {
		return switch (job.getMisfirePolicy()) {
			case ScheduleConstants.MISFIRE_DEFAULT -> cb;
			case ScheduleConstants.MISFIRE_IGNORE_MISFIRES -> cb.withMisfireHandlingInstructionIgnoreMisfires();
			case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED -> cb.withMisfireHandlingInstructionFireAndProceed();
			case ScheduleConstants.MISFIRE_DO_NOTHING -> cb.withMisfireHandlingInstructionDoNothing();
			default -> throw new TaskException(
					"The task misfire policy '" + job.getMisfirePolicy() + "' cannot be used in cron schedule tasks",
					Code.CONFIG_ERROR);
		};
	}

	/**
	 * 检查包名是否为白名单配置
	 * @param invokeTarget 目标字符串
	 * @return 结果
	 */
	public static boolean whiteList(String invokeTarget) {
		String packageName = StringUtils.substringBefore(invokeTarget, "(");
		int count = StringUtils.countMatches(packageName, ".");
		if (count > 1) {
			return StringUtils.containsAnyIgnoreCase(invokeTarget, Constants.JOB_WHITELIST_STR);
		}
		Object obj = SpringContextHolder.getBean(StringUtils.split(invokeTarget, ".")[0]);
		return StringUtils.containsAnyIgnoreCase(obj.getClass().getPackage().getName(), Constants.JOB_WHITELIST_STR);
	}

}
