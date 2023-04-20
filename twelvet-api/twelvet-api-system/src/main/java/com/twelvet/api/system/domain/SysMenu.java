package com.twelvet.api.system.domain;

import com.twelvet.framework.core.application.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 角色表 sys_menu
 */
@Schema(description = "角色表")
public class SysMenu extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@Schema(description = "菜单ID")
	private Long menuId;

	/**
	 * 菜单名称
	 */
	@Schema(description = "菜单名称")
	private String menuName;

	/**
	 * 父菜单名称
	 */
	@Schema(description = "父菜单名称")
	private String parentName;

	/**
	 * 父菜单ID
	 */
	@Schema(description = "父菜单ID")
	private Long parentId;

	/**
	 * 显示顺序
	 */
	@Schema(description = "显示顺序")
	private String orderNum;

	/**
	 * 路由地址
	 */
	@Schema(description = "路由地址")
	private String path;

	/**
	 * 组件路径
	 */
	@Schema(description = "组件路径")
	private String component;

	/**
	 * 是否为外链（1是 0否）
	 */
	@Schema(description = "是否为外链")
	private String isFrame;

	/**
	 * 类型（M目录 C菜单 F按钮）
	 */
	@Schema(description = "类型")
	private String menuType;

	/**
	 * 显示状态（1显示 0隐藏）
	 */
	@Schema(description = "显示状态")
	private String visible;

	/**
	 * 菜单状态（1显示 0隐藏）
	 */
	@Schema(description = "菜单状态")
	private String status;

	/**
	 * 权限字符串
	 */
	@Schema(description = "权限字符串")
	private String perms;

	/**
	 * 菜单图标
	 */
	@Schema(description = "菜单图标")
	private String icon;

	/**
	 * 子菜单
	 */
	@Schema(description = "子菜单")
	private List<SysMenu> routes = new ArrayList<>();

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getIsFrame() {
		return isFrame;
	}

	public void setIsFrame(String isFrame) {
		this.isFrame = isFrame;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<SysMenu> getRoutes() {
		return routes;
	}

	public void setRoutes(List<SysMenu> routes) {
		this.routes = routes;
	}

	@Override
	public String toString() {
		return "SysMenu{" + "menuId=" + menuId + ", menuName='" + menuName + '\'' + ", parentName='" + parentName + '\''
				+ ", parentId=" + parentId + ", orderNum='" + orderNum + '\'' + ", path='" + path + '\''
				+ ", component='" + component + '\'' + ", isFrame='" + isFrame + '\'' + ", menuType='" + menuType + '\''
				+ ", visible='" + visible + '\'' + ", status='" + status + '\'' + ", perms='" + perms + '\''
				+ ", icon='" + icon + '\'' + ", routes=" + routes + '}';
	}

}
