package com.twelvet.server.system.service.impl;

import com.twelvet.api.system.domain.SysMenu;
import com.twelvet.api.system.domain.SysUser;
import com.twelvet.api.system.domain.vo.MetaVo;
import com.twelvet.api.system.domain.vo.RouterVo;
import com.twelvet.api.system.domain.vo.TreeSelect;
import com.twelvet.framework.core.constants.UserConstants;
import com.twelvet.framework.datasource.annotation.ShardingDatasource;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.TUtils;
import com.twelvet.server.system.mapper.SysMenuMapper;
import com.twelvet.server.system.mapper.SysRoleMenuMapper;
import com.twelvet.server.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 菜单权限
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	@Autowired
	private SysMenuMapper menuMapper;

	@Autowired
	private SysRoleMenuMapper roleMenuMapper;

	/**
	 * 根据用户查询系统菜单列表
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	@Override
	public List<SysMenu> selectMenuList(Long userId) {
		return selectMenuList(new SysMenu(), userId);
	}

	/**
	 * 查询系统菜单列表
	 * @param menu 菜单信息
	 * @return 菜单列表
	 */
	@ShardingDatasource
	@Override
	public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
		List<SysMenu> menuList;
		// 管理员显示所有菜单信息
		if (SysUser.isAdmin(userId)) {
			menuList = menuMapper.selectMenuList(menu);
		}
		else {
			menu.getParams().put("userId", userId);
			menuList = menuMapper.selectMenuListByUserId(menu);
		}
		return menuList;
	}

	/**
	 * 根据用户ID查询权限
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	@Override
	public Set<String> selectMenuPermsByUserId(Long userId) {
		List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (TUtils.isNotEmpty(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;
	}

	/**
	 * 根据用户ID查询菜单
	 * @param userId 用户名称
	 * @return 菜单列表
	 */
	@Override
	public List<SysMenu> selectMenuTreeByUserId(Long userId) {
		List<SysMenu> menus;
		if (SecurityUtils.isAdmin(userId)) {
			menus = menuMapper.selectMenuTreeAll();
		}
		else {
			menus = menuMapper.selectMenuTreeByUserId(userId);
		}
		return getChildPerms(menus, 0);
	}

	/**
	 * 根据角色ID查询菜单树信息
	 * @param roleId 角色ID
	 * @return 选中菜单列表
	 */
	@Override
	public List<SysMenu> selectMenuListByRoleId(Long roleId) {
		return menuMapper.selectMenuListByRoleId(roleId);
	}

	/**
	 * 构建前端路由所需要的菜单
	 * @param menus 菜单列表
	 * @return 路由列表
	 */
	@Override
	public List<RouterVo> buildMenus(List<SysMenu> menus) {
		List<RouterVo> routers = new LinkedList<>();
		for (SysMenu menu : menus) {
			RouterVo router = new RouterVo();
			router.setHidden(menu.getVisible().equals("1"));
			router.setName(menu.getMenuName());
			router.setPath(getRouterPath(menu));
			router.setComponent(getComponent(menu));
			router.setIcon(menu.getIcon());
			router.setMenuType(menu.getMenuType());
			router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
			List<SysMenu> cMenus = menu.getRoutes();
			if (!cMenus.isEmpty() && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
				router.setAlwaysShow(true);
				router.setRedirect("noRedirect");
				router.setRoutes(buildMenus(cMenus));
			}
			routers.add(router);
		}
		return routers;
	}

	/**
	 * 构建前端所需要树结构
	 * @param menus 菜单列表
	 * @return 树结构列表
	 */
	@Override
	public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
		List<SysMenu> returnList = new ArrayList<>();
		for (SysMenu menu : menus) {
			// 根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (menu.getParentId() == 0) {
				recursionFn(menus, menu);
				returnList.add(menu);
			}
		}
		if (returnList.isEmpty()) {
			returnList = menus;
		}
		return returnList;
	}

	/**
	 * 构建前端所需要下拉树结构
	 * @param menus 菜单列表
	 * @return 下拉树结构列表
	 */
	@Override
	public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
		List<SysMenu> menuTrees = buildMenuTree(menus);
		return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
	}

	/**
	 * 根据菜单ID查询信息
	 * @param menuId 菜单ID
	 * @return 菜单信息
	 */
	@Override
	public SysMenu selectMenuById(Long menuId) {
		return menuMapper.selectMenuById(menuId);
	}

	/**
	 * 是否存在菜单子节点
	 * @param menuId 菜单ID
	 * @return 结果
	 */
	@Override
	public boolean hasChildByMenuId(Long menuId) {
		int result = menuMapper.hasChildByMenuId(menuId);
		return result > 0;
	}

	/**
	 * 查询菜单使用数量
	 * @param menuId 菜单ID
	 * @return 结果
	 */
	@Override
	public boolean checkMenuExistRole(Long menuId) {
		int result = roleMenuMapper.checkMenuExistRole(menuId);
		return result > 0;
	}

	/**
	 * 新增保存菜单信息
	 * @param menu 菜单信息
	 * @return 结果
	 */
	@Override
	public int insertMenu(SysMenu menu) {
		return menuMapper.insertMenu(menu);
	}

	/**
	 * 修改保存菜单信息
	 * @param menu 菜单信息
	 * @return 结果
	 */
	@Override
	public int updateMenu(SysMenu menu) {
		return menuMapper.updateMenu(menu);
	}

	/**
	 * 删除菜单管理信息
	 * @param menuId 菜单ID
	 * @return 结果
	 */
	@Override
	public int deleteMenuById(Long menuId) {
		return menuMapper.deleteMenuById(menuId);
	}

	/**
	 * 校验菜单名称是否唯一
	 * @param menu 菜单信息
	 * @return 结果
	 */
	@Override
	public String checkMenuNameUnique(SysMenu menu) {
		long menuId = TUtils.isEmpty(menu.getMenuId()) ? -1L : menu.getMenuId();
		SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
		if (TUtils.isNotEmpty(info) && info.getMenuId() != menuId) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 获取路由名称
	 * @param menu 菜单信息
	 * @return 路由名称
	 */
	private String getRouteName(SysMenu menu) {
		String routerName = menu.getPath();
		// 非外链并且是一级目录（类型为目录）
		if (isMenuFrame(menu)) {
			routerName = StringUtils.EMPTY;
		}
		return routerName;
	}

	/**
	 * 获取路由地址
	 * @param menu 菜单信息
	 * @return 路由地址
	 */
	private String getRouterPath(SysMenu menu) {
		String routerPath = menu.getPath();
		// 非外链并且是一级目录（类型为目录）
		if (isMenuFrame(menu)) {
			routerPath = "/";
		}
		return routerPath;
	}

	/**
	 * 获取组件信息
	 * @param menu 菜单信息
	 * @return 组件信息
	 */
	private String getComponent(SysMenu menu) {
		String component = UserConstants.LAYOUT;
		if (TUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
			component = menu.getComponent();
		}
		return component;
	}

	/**
	 * 是否为菜单内部跳转
	 * @param menu 菜单信息
	 * @return 结果
	 */
	private boolean isMenuFrame(SysMenu menu) {
		return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
				&& UserConstants.NO_FRAME.equals(menu.getIsFrame());
	}

	/**
	 * 根据父节点的ID获取所有子节点
	 * @param list 分类表
	 * @param parentId 传入的父节点ID
	 * @return String
	 */
	private List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
		List<SysMenu> returnList = new ArrayList<>();
		for (SysMenu t : list) {
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (t.getParentId() == parentId) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 * @param list List<SysMenu>
	 * @param t SysMenu
	 */
	private void recursionFn(List<SysMenu> list, SysMenu t) {
		// 得到子节点列表
		List<SysMenu> childList = getChildList(list, t);
		t.setRoutes(childList);
		for (SysMenu tChild : childList) {
			if (hasChild(list, tChild)) {
				// 判断是否有子节点
				for (SysMenu n : childList) {
					recursionFn(list, n);
				}
			}
		}
	}

	/**
	 * 得到子节点列表
	 */
	private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
		List<SysMenu> tlist = new ArrayList<>();
		for (SysMenu n : list) {
			if (n.getParentId().longValue() == t.getMenuId().longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<SysMenu> list, SysMenu t) {
		return getChildList(list, t).size() > 0;
	}

}
