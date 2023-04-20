package com.twelvet.framework.utils.exception;

import com.twelvet.framework.utils.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.Serial;
import java.io.StringWriter;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 基础异常
 */
public class TWTUtilsException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public TWTUtilsException(Throwable e) {
		super(e.getMessage(), e);
	}

	public TWTUtilsException(String message) {
		super(message);
	}

	public TWTUtilsException(String message, Throwable throwable) {
		super(message, throwable);
	}

	/**
	 * 获取exception的详细错误信息。
	 */
	public static String getExceptionMessage(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		return sw.toString();
	}

	public static String getRootErrorMseeage(Exception e) {
		Throwable root = ExceptionUtils.getRootCause(e);
		root = (root == null ? e : root);
		if (root == null) {
			return "";
		}
		String msg = root.getMessage();
		if (msg == null) {
			return "null";
		}
		return StringUtils.defaultString(msg);
	}

}
