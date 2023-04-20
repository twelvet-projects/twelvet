package com.twelvet.framework.core.exception;

import java.io.Serial;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 自定义404业务异常抛出
 */
public class NotFoundException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 所属模块
	 */
	private String module;

	/**
	 * 错误码
	 */
	private String code;

	/**
	 * 错误码对应的参数
	 */
	private Object[] args;

	/**
	 * 错误消息
	 */
	private String defaultMessage;

	public NotFoundException(String module, String code, Object[] args, String defaultMessage) {
		this.module = module;
		this.code = code;
		this.args = args;
		this.defaultMessage = defaultMessage;
	}

	public NotFoundException(String module, String code, Object[] args) {
		this(module, code, args, null);
	}

	public NotFoundException(String module, String defaultMessage) {
		this(module, null, null, defaultMessage);
	}

	public NotFoundException(String code, Object[] args) {
		this(null, code, args, null);
	}

	public NotFoundException(String defaultMessage) {
		this(null, null, null, defaultMessage);
	}

	public String getModule() {
		return module;
	}

	public String getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String getMessage() {
		return defaultMessage;
	}

}
