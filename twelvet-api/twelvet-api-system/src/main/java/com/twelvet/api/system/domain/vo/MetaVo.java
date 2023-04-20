package com.twelvet.api.system.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 路由显示信息
 */
@Schema(description = "路由显示信息")
public class MetaVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 设置该路由在侧边栏和面包屑中展示的名字
	 */
	@Schema(description = "展示的名字")
	private String title;

	/**
	 * 设置该路由的图标，对应路径src/icons/svg
	 */
	@Schema(description = "图标对应路径")
	private String icon;

	public MetaVo() {
	}

	public MetaVo(String title, String icon) {
		this.title = title;
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
