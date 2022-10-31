package com.twelvet.server.dfs.exception;

import java.io.Serial;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 文件名大小限制异常类
 */
public class FileSizeLimitExceededException extends FileException {

	@Serial
	private static final long serialVersionUID = 1L;

	public FileSizeLimitExceededException(long defaultMaxSize) {
		super("upload.exceed.maxSize", new Object[] { defaultMaxSize });
	}

}
