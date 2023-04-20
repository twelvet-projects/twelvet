package com.twelvet.server.system.controller;

import com.twelvet.api.system.domain.SysMenu;
import com.twelvet.api.system.domain.vo.RouterVo;
import com.twelvet.api.system.domain.vo.TreeSelect;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.server.system.service.ISysMenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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
 * @Description: 系统菜单控制器
 */
@Tag(description = "SysMenuController", name = "系统菜单控制器")
@RestController
@RequestMapping("/menu")
public class SysMenuController extends TWTController {

	@Autowired
	private ISysMenuService iSysMenuService;

	/**
	 * 新增菜单
	 * @param menu SysMenu
	 * @return 操作信息
	 */
	@Operation(summary = "新增菜单")
	@Log(service = "菜单管理", businessType = BusinessType.INSERT)
	@PostMapping
	@PreAuthorize("@role.hasPermi('system:menu:insert')")
	public JsonResult<String> insert(@Validated @RequestBody SysMenu menu) {
		if (UserConstants.NOT_UNIQUE.equals(iSysMenuService.checkMenuNameUnique(menu))) {
			throw new TWTException("新增菜单【" + menu.getMenuName() + "】失败，菜单名称已存在");
		}
		// 加入当前操作人员ID
		menu.setCreateBy(SecurityUtils.getUsername());
		return json(iSysMenuService.insertMenu(menu));
	}

	/**
	 * 删除菜单
	 * @param menuId menuId
	 * @return 操作提示
	 */
	@Operation(summary = "删除菜单")
	@Log(service = "菜单管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{menuId}")
	@PreAuthorize("@role.hasPermi('system:menu:remove')")
	public JsonResult<String> remove(@PathVariable("menuId") Long menuId) {
		if (iSysMenuService.hasChildByMenuId(menuId)) {
			return JsonResult.error("存在子菜单,不允许删除");
		}
		if (iSysMenuService.checkMenuExistRole(menuId)) {
			return JsonResult.error("菜单已分配角色,不允许删除");
		}
		return json(iSysMenuService.deleteMenuById(menuId));
	}

	/**
	 * 修改菜单
	 * @param menu SysMenu
	 * @return JsonResult<String>
	 */
	@Operation(summary = "修改菜单")
	@Log(service = "菜单管理", businessType = BusinessType.UPDATE)
	@PutMapping
	@PreAuthorize("@role.hasPermi('system:menu:update')")
	public JsonResult<String> update(@Validated @RequestBody SysMenu menu) {
		if (UserConstants.NOT_UNIQUE.equals(iSysMenuService.checkMenuNameUnique(menu))) {
			throw new TWTException("新增菜单【" + menu.getMenuName() + "】失败，菜单名称已存在");
		}
		menu.setUpdateBy(SecurityUtils.getUsername());
		return json(iSysMenuService.updateMenu(menu));
	}

	/**
	 * 获取菜单列表
	 * @param sysMenu sysMenu
	 * @return 菜单数据
	 */
	@Operation(summary = "获取菜单列表")
	@GetMapping("/list")
	@PreAuthorize("@role.hasPermi('system:menu:list')")
	public JsonResult<List<SysMenu>> list(SysMenu sysMenu) {
		LoginUser loginUser = SecurityUtils.getLoginUser();
		Long userId = loginUser.getUserId();
		List<SysMenu> menus = iSysMenuService.selectMenuList(sysMenu, userId);
		return JsonResult.success(menus);
	}

	/**
	 * 根据ID获取菜单信息
	 * @param menuId menuId
	 * @return 操心信息
	 */
	@Operation(summary = "根据ID获取菜单信息")
	@GetMapping(value = "/{menuId}")
	@PreAuthorize("@role.hasPermi('system:menu:query')")
	public JsonResult<SysMenu> getByMenuId(@PathVariable Long menuId) {
		return JsonResult.success(iSysMenuService.selectMenuById(menuId));
	}

	/**
	 * 加载对应角色菜单列表树
	 * @param roleId 角色ID
	 * @return JsonResult
	 */
	@Operation(summary = "加载对应角色菜单列表树")
	@GetMapping(value = "/roleMenuTreeSelect/{roleId}")
	@PreAuthorize("@role.hasPermi('system:menu:list')")
	public AjaxResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
		LoginUser loginUser = SecurityUtils.getLoginUser();
		Long userId = loginUser.getUserId();
		List<SysMenu> menus = iSysMenuService.selectMenuList(userId);

		Map<String, Object> res = new HashMap<>(2);
		res.put("checkedMenus", iSysMenuService.selectMenuListByRoleId(roleId));
		res.put("menus", iSysMenuService.buildMenuTreeSelect(menus));

		return AjaxResult.success(res);
	}

	/**
	 * 获取菜单下拉树列表
	 * @param menu SysMenu
	 * @return JsonResult<List < TreeSelect>>
	 */
	@Operation(summary = "获取菜单下拉树列表")
	@GetMapping("/treeSelect")
	@PreAuthorize("@role.hasPermi('system:menu:list')")
	public JsonResult<List<TreeSelect>> treeSelect(SysMenu menu) {
		LoginUser loginUser = SecurityUtils.getLoginUser();
		Long userId = loginUser.getUserId();
		List<SysMenu> menus = iSysMenuService.selectMenuList(menu, userId);
		return JsonResult.success(iSysMenuService.buildMenuTreeSelect(menus));
	}

	/**
	 * 获取路由信息
	 * @return 路由信息
	 */
	@GetMapping("getRouters")
	public JsonResult<List<RouterVo>> getRouters() {
		Long userId = SecurityUtils.getLoginUser().getUserId();
		// 路由菜单
		List<SysMenu> menus = iSysMenuService.selectMenuTreeByUserId(userId);
		return JsonResult.success(iSysMenuService.buildMenus(menus));
	}

}
