package com.twelvet.server.system.controller;

import com.twelvet.api.system.domain.SysDept;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.server.system.service.ISysDeptService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 系统操作/访问日志
 */
@RestController
@RequestMapping("/dept")
public class SysDeptController extends TWTController {

    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     *
     * @param dept SysDept
     * @return AjaxResult
     */
    @GetMapping("/list")
    @PreAuthorize("@role.hasPermi('system:dept:list')")
    public AjaxResult list(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(depts);
    }

    /**
     * 查询部门列表（排除节点）
     *
     * @param deptId 部门ID
     * @return AjaxResult
     */
    @GetMapping("/list/exclude/{deptId}")
    @PreAuthorize("@role.hasPermi('system:dept:query')")
    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        depts.removeIf(d -> d.getDeptId().intValue() == deptId
                || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return AjaxResult.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     *
     * @param deptId 部门ID
     * @return AjaxResult
     */
    @GetMapping(value = "/{deptId}")
    @PreAuthorize("@role.hasPermi('system:dept:query')")
    public AjaxResult getInfo(@PathVariable Long deptId) {
        return AjaxResult.success(deptService.selectDeptById(deptId));
    }

    /**
     * 获取部门下拉树列表
     *
     * @param dept SysDept
     * @return AjaxResult
     */
    @GetMapping("/treeSelect")
    public AjaxResult treeSelect(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
    }

    /**
     * 加载对应角色部门列表树
     *
     * @param roleId 部门ID
     * @return AjaxResult
     */
    @GetMapping(value = "/roleDeptTreeSelect/{roleId}")
    public AjaxResult roleDeptTreeSelect(@PathVariable("roleId") Long roleId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());

        Map<String, Object> res = new HashMap<>(2);
        res.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        res.put("depts", deptService.buildDeptTreeSelect(depts));
        return AjaxResult.success(res);
    }

    /**
     * 新增部门
     *
     * @param dept SysDept
     * @return AjaxResult
     */
    @Log(service = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    @PreAuthorize("@role.hasPermi('system:dept:insert')")
    public AjaxResult insert(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(SecurityUtils.getUsername());
        return json(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     *
     * @param dept SysDept
     * @return AjaxResult
     */
    @Log(service = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @PreAuthorize("@role.hasPermi('system:dept:update')")
    public AjaxResult update(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return AjaxResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
                && deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
            return AjaxResult.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(SecurityUtils.getUsername());
        return json(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     * @return AjaxResult
     */
    @Log(service = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    @PreAuthorize("@role.hasPermi('system:dept:remove')")
    public AjaxResult remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return AjaxResult.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return AjaxResult.error("部门存在用户,不允许删除");
        }
        return json(deptService.deleteDeptById(deptId));
    }

}