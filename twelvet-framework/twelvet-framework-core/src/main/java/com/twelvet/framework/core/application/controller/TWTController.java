package com.twelvet.framework.core.application.controller;

import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.core.application.domain.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 基础控制器
 */
public class TWTController {

	protected final Logger logger = LoggerFactory.getLogger(TWTController.class);

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				// setValue(DateUtil.parseDate(text));
			}
		});
	}

	/**
	 * 响应返回结果
	 * @param rows 影响行数
	 * @return 操作结果
	 */
	protected JsonResult<String> json(int rows) {
		return rows > 0 ? JsonResult.success() : JsonResult.error();
	}

	/**
	 * 返回失败消息
	 */
	public JsonResult<String> error(String message) {
		return JsonResult.error(message);
	}

}
