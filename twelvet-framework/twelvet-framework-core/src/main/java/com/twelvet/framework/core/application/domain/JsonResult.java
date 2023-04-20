package com.twelvet.framework.core.application.domain;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 统一操作信息实体(支持Swagger响应泛型)
 */
public class JsonResult<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 状态码
	 */
	private int code;

	/**
	 * 返回内容信息
	 */
	private String msg;

	/**
	 * 数据对象
	 */
	private T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 返回成功消息
	 * @return 成功消息
	 */
	public static <T> JsonResult<T> success() {
		return JsonResult.success("操作成功");
	}

	/**
	 * 返回成功数据
	 * @return 成功消息
	 */
	public static <T> JsonResult<T> success(T data) {
		return JsonResult.success("操作成功", data);
	}

	/**
	 * 返回成功消息
	 * @param msg 返回内容
	 * @return 成功消息
	 */
	public static <T> JsonResult<T> success(String msg) {
		return JsonResult.success(msg, null);
	}

	/**
	 * 返回成功消息
	 * @param msg 返回内容
	 * @param data 数据对象
	 * @return 成功消息
	 */
	public static <T> JsonResult<T> success(String msg, T data) {
		return restJson(HttpStatus.OK.value(), msg, data);
	}

	/**
	 * 返回错误消息
	 * @return JsonResult
	 */
	public static <T> JsonResult<T> error() {
		return JsonResult.error("操作失败");
	}

	/**
	 * 返回错误消息
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static <T> JsonResult<T> error(String msg) {
		return JsonResult.error(msg, null);
	}

	/**
	 * 返回错误消息
	 * @param msg 返回内容
	 * @param data 数据对象
	 * @return 警告消息StringUtil
	 */
	public static <T> JsonResult<T> error(String msg, T data) {
		return restJson(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
	}

	/**
	 * 返回错误消息
	 * @param code 状态码
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static <T> JsonResult<T> error(int code, String msg) {
		return restJson(code, msg, null);
	}

	/**
	 * 实例化Json
	 * @param data 返回数据
	 * @param code 响应码
	 * @param msg 响应信息
	 * @return 响应Json数据
	 */
	public static <T> JsonResult<T> restJson(int code, String msg, T data) {
		JsonResult<T> apiJson = new JsonResult<>();
		apiJson.setCode(code);
		apiJson.setData(data);
		apiJson.setMsg(msg);
		return apiJson;
	}

}
