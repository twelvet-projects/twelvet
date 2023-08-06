package com.twelvet.server.system.controller;

import com.twelvet.api.system.domain.SysDept;
import com.twelvet.api.system.domain.vo.TreeSelect;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.server.system.service.ISysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * @WebSite twelvet.cn
 * @Description: 部门管理
 */
@Tag(description = "SysDeptController", name = "部门管理")
@RestController
@RequestMapping("/dept")
public class SysDeptController extends TWTController {

	@Autowired
	private ISysDeptService deptService;

	/**
	 * 获取部门列表
	 * @param dept SysDept
	 * @return JsonResult<List < SysDept>>
	 */
	@Operation(summary = "获取部门列表")
	@GetMapping("/list")
	@PreAuthorize("@role.hasPermi('system:dept:list')")
	public JsonResult<List<SysDept>> list(SysDept dept) {
		List<SysDept> depts = deptService.selectDeptList(dept);
		return JsonResult.success(depts);
	}

	/**
	 * 查询部门列表（排除节点）
	 * @param deptId 部门ID
	 * @return JsonResult<List < SysDept>>
	 */
	@Operation(summary = "查询部门列表")
	@GetMapping("/list/exclude/{deptId}")
	@PreAuthorize("@role.hasPermi('system:dept:query')")
	public JsonResult<List<SysDept>> excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
		List<SysDept> depts = deptService.selectDeptList(new SysDept());
		depts.removeIf(d -> d.getDeptId().intValue() == deptId
				|| ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
		return JsonResult.success(depts);
	}

	/**
	 * 根据部门编号获取详细信息
	 * @param deptId 部门ID
	 * @return JsonResult<SysDept>
	 */
	@Operation(summary = "根据部门编号获取详细信息")
	@GetMapping(value = "/{deptId}")
	@PreAuthorize("@role.hasPermi('system:dept:query')")
	public JsonResult<SysDept> getInfo(@PathVariable Long deptId) {
		deptService.checkDeptDataScope(deptId);
		return JsonResult.success(deptService.selectDeptById(deptId));
	}

	/**
	 * 获取部门下拉树列表
	 * @param dept SysDept
	 * @return JsonResult<List < TreeSelect>>
	 */
	@Operation(summary = "获取部门下拉树列表")
	@GetMapping("/treeSelect")
	public JsonResult<List<TreeSelect>> treeSelect(SysDept dept) {
		List<SysDept> depts = deptService.selectDeptList(dept);
		return JsonResult.success(deptService.buildDeptTreeSelect(depts));
	}

	/**
	 * 加载对应角色部门列表树
	 * @param roleId 部门ID
	 * @return AjaxResult
	 */
	@Operation(summary = "加载对应角色部门列表树")
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
	 * @param dept SysDept
	 * @return JsonResult<String>
	 */
	@Operation(summary = "新增部门")
	@Log(service = "部门管理", businessType = BusinessType.INSERT)
	@PostMapping
	@PreAuthorize("@role.hasPermi('system:dept:insert')")
	public JsonResult<String> insert(@Validated @RequestBody SysDept dept) {
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return JsonResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		}
		dept.setCreateBy(SecurityUtils.getUsername());
		return json(deptService.insertDept(dept));
	}

	/**
	 * 修改部门
	 * @param dept SysDept
	 * @return JsonResult<String>
	 */
	@Operation(summary = "修改部门")
	@Log(service = "部门管理", businessType = BusinessType.UPDATE)
	@PutMapping
	@PreAuthorize("@role.hasPermi('system:dept:update')")
	public JsonResult<String> update(@Validated @RequestBody SysDept dept) {
		Long deptId = dept.getDeptId();
		deptService.checkDeptDataScope(deptId);
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return JsonResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		}
		else if (dept.getParentId().equals(dept.getDeptId())) {
			return JsonResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
		}
		else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
				&& deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
			return JsonResult.error("该部门包含未停用的子部门！");
		}
		dept.setUpdateBy(SecurityUtils.getUsername());
		return json(deptService.updateDept(dept));
	}

	/**
	 * 删除部门
	 * @param deptId 部门ID
	 * @return JsonResult<String>
	 */
	@Operation(summary = "删除部门")
	@Log(service = "部门管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deptId}")
	@PreAuthorize("@role.hasPermi('system:dept:remove')")
	public JsonResult<String> remove(@PathVariable Long deptId) {
		if (deptService.hasChildByDeptId(deptId)) {
			return JsonResult.error("存在下级部门,不允许删除");
		}
		if (deptService.checkDeptExistUser(deptId)) {
			return JsonResult.error("部门存在用户,不允许删除");
		}
		deptService.checkDeptDataScope(deptId);
		return json(deptService.deleteDeptById(deptId));
	}

}
