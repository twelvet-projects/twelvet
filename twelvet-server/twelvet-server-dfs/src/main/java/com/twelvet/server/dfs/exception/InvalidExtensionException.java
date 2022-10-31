package com.twelvet.server.dfs.exception;

import org.apache.commons.fileupload.FileUploadException;

import java.io.Serial;
import java.util.Arrays;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 文件上传 误异常类
 */
public class InvalidExtensionException extends FileUploadException {

	@Serial
	private static final long serialVersionUID = 1L;

	private String[] allowedExtension;

	private String extension;

	private String filename;

	public InvalidExtensionException(String[] allowedExtension, String extension, String filename) {
		super("filename : [" + filename + "], extension : [" + extension + "], allowed extension : ["
				+ Arrays.toString(allowedExtension) + "]");
		this.allowedExtension = allowedExtension;
		this.extension = extension;
		this.filename = filename;
	}

	public String[] getAllowedExtension() {
		return allowedExtension;
	}

	public String getExtension() {
		return extension;
	}

	public String getFilename() {
		return filename;
	}

	public static class InvalidImageExtensionException extends InvalidExtensionException {

		@Serial
		private static final long serialVersionUID = 1L;

		public InvalidImageExtensionException(String[] allowedExtension, String extension, String filename) {
			super(allowedExtension, extension, filename);
		}

	}

	public static class InvalidFlashExtensionException extends InvalidExtensionException {

		@Serial
		private static final long serialVersionUID = 1L;

		public InvalidFlashExtensionException(String[] allowedExtension, String extension, String filename) {
			super(allowedExtension, extension, filename);
		}

	}

	public static class InvalidMediaExtensionException extends InvalidExtensionException {

		@Serial
		private static final long serialVersionUID = 1L;

		public InvalidMediaExtensionException(String[] allowedExtension, String extension, String filename) {
			super(allowedExtension, extension, filename);
		}

	}

	public static class InvalidVideoExtensionException extends InvalidExtensionException {

		@Serial
		private static final long serialVersionUID = 1L;

		public InvalidVideoExtensionException(String[] allowedExtension, String extension, String filename) {
			super(allowedExtension, extension, filename);
		}

	}

}
