package com.twelvet.framework.core.application.domain;

import com.twelvet.framework.core.locale.I18nUtils;
import com.twelvet.framework.core.locale.constants.LocaleSystemConstants;
import com.twelvet.framework.utils.TUtils;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 统一操作信息实体
 */
public class AjaxResult extends LinkedHashMap<String, Object> {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 状态码
	 */
	public static final String CODE_TAG = "code";

	/**
	 * 返回内容
	 */
	public static final String MSG_TAG = "msg";

	/**
	 * 数据对象
	 */
	public static final String DATA_TAG = "data";

	/**
	 * 访问路径
	 */
	public static final String PATH_TAG = "path";

	/**
	 * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
	 */
	public AjaxResult() {
	}

	/**
	 * 初始化一个新创建的 AjaxResult 对象
	 * @param code 状态码
	 * @param msg 返回内容
	 */
	public AjaxResult(int code, String msg) {
		super.put(CODE_TAG, code);
		super.put(MSG_TAG, msg);
	}

	/**
	 * 初始化一个新创建的 AjaxResult 对象
	 * @param code 状态码
	 * @param msg 返回内容
	 * @param data 数据对象
	 */
	public AjaxResult(int code, String msg, Object data) {
		super.put(CODE_TAG, code);
		super.put(MSG_TAG, msg);
		if (!Objects.isNull(data)) {
			super.put(DATA_TAG, data);
		}
	}

	/**
	 * 返回成功消息
	 * @return 成功消息
	 */
	public static AjaxResult success() {
		return AjaxResult.success(I18nUtils.getLocale(LocaleSystemConstants.SYS_SUCCESS));
	}

	/**
	 * 返回成功数据
	 * @return 成功消息
	 */
	public static AjaxResult success(Object data) {
		return AjaxResult.success(I18nUtils.getLocale(LocaleSystemConstants.SYS_SUCCESS), data);
	}

	/**
	 * 返回成功消息
	 * @param msg 返回内容
	 * @return 成功消息
	 */
	public static AjaxResult success(String msg) {
		return AjaxResult.success(msg, null);
	}

	/**
	 * 返回成功消息
	 * @param msg 返回内容
	 * @param data 数据对象
	 * @return 成功消息
	 */
	public static AjaxResult success(String msg, Object data) {
		return new AjaxResult(HttpStatus.OK.value(), msg, data);
	}

	/**
	 * 返回错误消息
	 * @return JsonResult
	 */
	public static AjaxResult error() {
		return AjaxResult.error(I18nUtils.getLocale(LocaleSystemConstants.SYS_ERROR));
	}

	/**
	 * 返回错误消息
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static AjaxResult error(String msg) {
		return AjaxResult.error(msg, null);
	}

	/**
	 * 返回错误消息
	 * @param msg 返回内容
	 * @param data 数据对象
	 * @return 警告消息StringUtil
	 */
	public static AjaxResult error(String msg, Object data) {
		return new AjaxResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
	}

	/**
	 * 返回错误消息
	 * @param code 状态码
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static AjaxResult error(int code, String msg) {
		return new AjaxResult(code, msg, null);
	}

}
