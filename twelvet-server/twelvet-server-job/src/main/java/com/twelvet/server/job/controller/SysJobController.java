package com.twelvet.server.job.controller;

import com.twelvet.api.job.domain.SysJob;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.poi.ExcelUtils;
import com.twelvet.server.job.exception.TaskException;
import com.twelvet.server.job.service.ISysJobService;
import com.twelvet.server.job.util.CronUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 调度任务信息操作处理
 */
@RestController
@RequestMapping("/cron")
public class SysJobController extends TWTController {

    @Autowired
    private ISysJobService jobService;

    /**
     * 查询定时任务列表
     *
     * @param sysJob SysJob
     * @return AjaxResult
     */
    @GetMapping("/pageQuery")
    @PreAuthorize("@role.hasPermi('monitor:job:list')")
    public AjaxResult pageQuery(SysJob sysJob) {
        startPage();
        List<SysJob> list = jobService.selectJobList(sysJob);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 导出定时任务列表
     *
     * @param response HttpServletResponse
     * @param sysJob   SysJob
     */
    @Log(service = "定时任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @PreAuthorize("@role.hasPermi('monitor:job:export')")
    public void export(HttpServletResponse response, @RequestBody SysJob sysJob) {
        List<SysJob> list = jobService.selectJobList(sysJob);
        ExcelUtils<SysJob> excelUtils = new ExcelUtils<>(SysJob.class);
        excelUtils.exportExcel(response, list, "定时任务");
    }

    /**
     * 获取定时任务详细信息
     *
     * @param jobId 定时任务ID
     * @return AjaxResult
     */
    @GetMapping(value = "/{jobId}")
    @PreAuthorize("@role.hasPermi('monitor:job:query')")
    public AjaxResult getByJobId(@PathVariable("jobId") Long jobId) {
        return AjaxResult.success(jobService.selectJobById(jobId));
    }

    /**
     * 新增定时任务
     *
     * @param sysJob SysJob
     * @return AjaxResult
     * @throws SchedulerException 表达式异常
     * @throws TaskException      任务异常
     */
    @Log(service = "定时任务", businessType = BusinessType.INSERT)
    @PostMapping
    @PreAuthorize("@role.hasPermi('monitor:job:insert')")
    public AjaxResult insert(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(sysJob.getCronExpression())) {
            return AjaxResult.error("cron表达式不正确");
        }
        sysJob.setCreateBy(SecurityUtils.getUsername());
        return json(jobService.insertJob(sysJob));
    }

    /**
     * 修改定时任务
     *
     * @param sysJob SysJob
     * @return AjaxResult
     * @throws SchedulerException 表达式异常
     * @throws TaskException      任务异常
     */
    @Log(service = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping
    @PreAuthorize("@role.hasPermi('monitor:job:update')")
    public AjaxResult update(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(sysJob.getCronExpression())) {
            return AjaxResult.error("cron表达式不正确");
        }
        sysJob.setUpdateBy(SecurityUtils.getUsername());
        return json(jobService.updateJob(sysJob));
    }

    /**
     * 定时任务状态修改
     *
     * @param job SysJob
     * @return AjaxResult
     * @throws SchedulerException 表达式异常
     */
    @Log(service = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @PreAuthorize("@role.hasPermi('monitor:job:update')")
    public AjaxResult changeStatus(@RequestBody SysJob job) throws SchedulerException {
        SysJob newJob = jobService.selectJobById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return json(jobService.changeStatus(newJob));
    }

    /**
     * 定时任务立即执行一次
     *
     * @param job SysJob
     * @return AjaxResult
     * @throws SchedulerException 表达式异常
     */
    @Log(service = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/run")
    public AjaxResult run(@RequestBody SysJob job) throws SchedulerException {
        jobService.run(job);
        return AjaxResult.success();
    }

    /**
     * 删除定时任务
     *
     * @param jobIds 定时任务id数组
     * @return AjaxResult
     * @throws SchedulerException 表达式异常
     */
    @Log(service = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobIds}")
    @PreAuthorize("@role.hasPermi('monitor:job:remove')")
    public AjaxResult remove(@PathVariable Long[] jobIds) throws SchedulerException {
        jobService.deleteJobByIds(jobIds);
        return AjaxResult.success();
    }
}
