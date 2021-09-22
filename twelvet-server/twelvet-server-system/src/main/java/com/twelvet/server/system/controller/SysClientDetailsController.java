package com.twelvet.server.system.controller;

import com.twelvet.api.system.domain.SysClientDetails;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.TWTUtils;
import com.twelvet.server.system.service.ISysClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 终端配置 信息操作处理
 */
@RestController
@RequestMapping("/client")
public class SysClientDetailsController extends TWTController {

    @Autowired
    private ISysClientDetailsService sysClientDetailsService;

    /**
     * 查询终端配置列表
     *
     * @param sysClientDetails SysClientDetails
     * @return AjaxResult
     */
    @PreAuthorize("@role.hasPermi('system:client:list')")
    @GetMapping("/pageQuery")
    public AjaxResult pageQuery(SysClientDetails sysClientDetails) {
        startPage();
        List<SysClientDetails> list = sysClientDetailsService.selectSysClientDetailsList(sysClientDetails);
        return AjaxResult.success(getDataTable(list));
    }

    /**
     * 获取终端配置详细信息
     *
     * @param clientId 终端ID
     * @return AjaxResult
     */
    @PreAuthorize("@role.hasPermi('system:client:query')")
    @GetMapping(value = "/{clientId}")
    public AjaxResult getInfo(@PathVariable("clientId") String clientId) {
        return AjaxResult.success(sysClientDetailsService.selectSysClientDetailsById(clientId));
    }

    /**
     * 新增终端配置
     *
     * @param sysClientDetails SysClientDetails
     * @return AjaxResult
     */
    @PreAuthorize("@role.hasPermi('system:client:insert')")
    @Log(service = "终端配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult insert(@RequestBody SysClientDetails sysClientDetails) {
        String clientId = sysClientDetails.getClientId();
        if (StringUtils.isNotNull(sysClientDetailsService.selectSysClientDetailsById(clientId))) {
            return AjaxResult.error("新增终端'" + clientId + "'失败，编号已存在");
        }
        sysClientDetails.setClientSecret(SecurityUtils.encryptPassword(sysClientDetails.getClientSecret()));
        return json(sysClientDetailsService.insertSysClientDetails(sysClientDetails));
    }

    /**
     * 修改终端配置
     *
     * @param sysClientDetails sysClientDetails
     * @return AjaxResult
     */
    @PreAuthorize("@role.hasPermi('system:client:update')")
    @Log(service = "终端配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@RequestBody SysClientDetails sysClientDetails) {
        // 重新设置密码
        if (TWTUtils.isNotEmpty(sysClientDetails.getClientSecret())) {
            sysClientDetails.setClientSecret(SecurityUtils.encryptPassword(sysClientDetails.getClientSecret()));
        }

        return json(sysClientDetailsService.updateSysClientDetails(sysClientDetails));
    }

    /**
     * 删除终端配置
     *
     * @param clientIds 终端ID数组
     * @return 成功删除个数
     */
    @PreAuthorize("@role.hasPermi('system:client:remove')")
    @Log(service = "终端配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{clientIds}")
    public AjaxResult remove(@PathVariable String[] clientIds) {
        return json(sysClientDetailsService.deleteSysClientDetailsByIds(clientIds));
    }
}