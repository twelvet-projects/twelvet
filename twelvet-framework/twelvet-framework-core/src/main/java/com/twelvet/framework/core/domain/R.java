package com.twelvet.framework.core.domain;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 响应信息实体
 */
public class R<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/** 成功 */
	public static final int SUCCESS = HttpStatus.OK.value();

	/** 失败 */
	public static final int FAIL = HttpStatus.INTERNAL_SERVER_ERROR.value();

	private int code;

	private String msg;

	private T data;

	public static <T> R<T> ok() {
		return restResult(null, SUCCESS, null);
	}

	public static <T> R<T> ok(T data) {
		return restResult(data, SUCCESS, null);
	}

	public static <T> R<T> ok(T data, String msg) {
		return restResult(data, SUCCESS, msg);
	}

	public static <T> R<T> fail() {
		return restResult(null, FAIL, null);
	}

	public static <T> R<T> fail(String msg) {
		return restResult(null, FAIL, msg);
	}

	public static <T> R<T> fail(T data) {
		return restResult(data, FAIL, null);
	}

	public static <T> R<T> fail(T data, String msg) {
		return restResult(data, FAIL, msg);
	}

	public static <T> R<T> fail(int code, String msg) {
		return restResult(null, code, msg);
	}

	public static <T> R<T> restResult(T data, int code, String msg) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}

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

}
