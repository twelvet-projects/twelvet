package com.twelvet.server.system.controller.api;

import com.twelvet.api.system.domain.SysDept;
import com.twelvet.api.system.domain.vo.TreeSelect;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.server.system.service.ISysDeptService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 部门管理api
 */
@AuthIgnore
@Hidden
@Tag(description = "SysDeptApi", name = "部门管理api")
@RestController
@RequestMapping("/api/dept")
public class SysDeptApi extends TWTController {

	@Autowired
	private ISysDeptService deptService;

	/**
	 * 获取当前用户持有的权限列表
	 * @return JsonResult<List < SysDept>>
	 */
	/**
	 * 获取当前用户持有的权限列表
	 * @return JsonResult<List < SysDept>>
	 */
	@Operation(summary = "获取当前用户持有的权限列表")
	@GetMapping("/current/user/ids")
	public R<Set<Long>> selectDeptIdListByUser() {
		SysDept sysDept = new SysDept();
		return R.ok(deptService.selectDeptIdListByUser(sysDept));
	}

}
