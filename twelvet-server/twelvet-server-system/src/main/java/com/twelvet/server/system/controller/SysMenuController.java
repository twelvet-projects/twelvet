package com.twelvet.server.system.controller;

import com.twelvet.api.system.domain.SysMenu;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.framework.security.domain.LoginUser;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.server.system.service.ISysMenuService;
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
 * @Description: 系统菜单控制器
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController extends TWTController {

    @Autowired
    private ISysMenuService iSysMenuService;

    /**
     * 新增菜单
     *
     * @param menu SysMenu
     * @return 操作信息
     */
    @Log(service = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    @PreAuthorize("@role.hasPermi('system:menu:insert')")
    public AjaxResult insert(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(iSysMenuService.checkMenuNameUnique(menu))) {
            throw new TWTException("新增菜单【" + menu.getMenuName() + "】失败，菜单名称已存在");
        }
        // 加入当前操作人员ID
        menu.setCreateBy(SecurityUtils.getUsername());
        return json(iSysMenuService.insertMenu(menu));
    }

    /**
     * 删除菜单
     *
     * @param menuId menuId
     * @return 操作提示
     */
    @Log(service = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    @PreAuthorize("@role.hasPermi('system:menu:remove')")
    public AjaxResult remove(@PathVariable("menuId") Long menuId) {
        if (iSysMenuService.hasChildByMenuId(menuId)) {
            return AjaxResult.error("存在子菜单,不允许删除");
        }
        if (iSysMenuService.checkMenuExistRole(menuId)) {
            return AjaxResult.error("菜单已分配角色,不允许删除");
        }
        return json(iSysMenuService.deleteMenuById(menuId));
    }

    /**
     * 修改菜单
     *
     * @param menu SysMenu
     * @return AjaxResult
     */
    @Log(service = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @PreAuthorize("@role.hasPermi('system:menu:update')")
    public AjaxResult update(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(iSysMenuService.checkMenuNameUnique(menu))) {
            throw new TWTException("新增菜单【" + menu.getMenuName() + "】失败，菜单名称已存在");
        }
        menu.setUpdateBy(SecurityUtils.getUsername());
        return json(iSysMenuService.updateMenu(menu));
    }

    /**
     * 获取菜单列表
     *
     * @param sysMenu sysMenu
     * @return 菜单数据
     */
    @GetMapping("/list")
    @PreAuthorize("@role.hasPermi('system:menu:list')")
    public AjaxResult list(SysMenu sysMenu) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        List<SysMenu> menus = iSysMenuService.selectMenuList(sysMenu, userId);
        return AjaxResult.success(menus);
    }

    /**
     * 根据ID获取菜单信息
     *
     * @param menuId menuId
     * @return 操心信息
     */
    @GetMapping(value = "/{menuId}")
    @PreAuthorize("@role.hasPermi('system:menu:query')")
    public AjaxResult getByMenuId(@PathVariable Long menuId) {
        return AjaxResult.success(iSysMenuService.selectMenuById(menuId));
    }

    /**
     * 加载对应角色菜单列表树
     *
     * @param roleId 角色ID
     * @return AjaxResult
     */
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
     *
     * @param menu SysMenu
     * @return AjaxResult
     */
    @GetMapping("/treeSelect")
    @PreAuthorize("@role.hasPermi('system:menu:list')")
    public AjaxResult treeSelect(SysMenu menu) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        List<SysMenu> menus = iSysMenuService.selectMenuList(menu, userId);
        return AjaxResult.success(iSysMenuService.buildMenuTreeSelect(menus));
    }

}
