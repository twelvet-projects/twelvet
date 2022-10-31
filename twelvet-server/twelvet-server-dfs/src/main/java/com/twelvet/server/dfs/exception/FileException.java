package com.twelvet.server.dfs.exception;

import com.twelvet.framework.core.exception.TWTException;

import java.io.Serial;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 文件信息异常类
 */
public class FileException extends TWTException {

	@Serial
	private static final long serialVersionUID = 1L;

	public FileException(String code, Object[] args) {
		super("file", code, args, null);
	}

}
