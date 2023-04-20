package com.twelvet.framework.utils.file;

import com.twelvet.framework.utils.TUtils;
import com.twelvet.framework.utils.DateUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 文件处理工具类
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

	/**
	 * 字符常量：斜杠 {@code '/'}
	 */
	public static final char SLASH = '/';

	/**
	 * 字符常量：反斜杠 {@code '\\'}
	 */
	public static final char BACKSLASH = '\\';

	public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

	/**
	 * 输出指定文件的byte数组
	 * @param filePath 文件路径
	 * @param os 输出流
	 */
	public static void writeBytes(String filePath, OutputStream os) throws IOException {
		FileInputStream fis = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new FileNotFoundException(filePath);
			}
			fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int length;
			while ((length = fis.read(b)) > 0) {
				os.write(b, 0, length);
			}
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			if (os != null) {
				try {
					os.close();
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除文件
	 * @param filePath 文件
	 */
	public static boolean deleteFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			boolean delete = file.delete();
			// 删除成功
			if (delete) {
				flag = true;
			}

		}
		return flag;
	}

	/**
	 * 文件名称验证
	 * @param filename 文件名称
	 * @return true 正常 false 非法
	 */
	public static boolean isValidFilename(String filename) {
		return filename.matches(FILENAME_PATTERN);
	}

	/**
	 * 检查文件是否可下载
	 * @param resource 需要下载的文件
	 * @return true 正常 false 非法
	 */
	public static boolean checkAllowDownload(String resource) {
		// 禁止目录上跳级别
		if (StringUtils.contains(resource, "..")) {
			return false;
		}

		// 检查允许下载的文件规则是否允许
		return ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, FileTypeUtils.getFileType(resource));
	}

	/**
	 * 下载文件名重新编码
	 * @param request 请求对象
	 * @param fileName 文件名
	 * @return 编码后的文件名
	 */
	public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
			throws UnsupportedEncodingException {
		final String agent = request.getHeader("USER-AGENT");
		String filename = fileName;
		if (agent.contains("MSIE")) {
			// IE浏览器
			filename = URLEncoder.encode(filename, "utf-8");
			filename = filename.replace("+", " ");
		}
		else if (agent.contains("Firefox")) {
			// 火狐浏览器
			filename = new String(fileName.getBytes(), "ISO8859-1");
		}
		else if (agent.contains("Chrome")) {
			// google浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		}
		else {
			// 其它浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}

	/**
	 * 返回文件名
	 * @param filePath 文件
	 * @return 文件名
	 */
	public static String getName(String filePath) {
		if (null == filePath) {
			return null;
		}
		int len = filePath.length();
		if (0 == len) {
			return filePath;
		}
		if (isFileSeparator(filePath.charAt(len - 1))) {
			// 以分隔符结尾的去掉结尾分隔符
			len--;
		}

		int begin = 0;
		char c;
		for (int i = len - 1; i > -1; i--) {
			c = filePath.charAt(i);
			if (isFileSeparator(c)) {
				// 查找最后一个路径分隔符（/或者\）
				begin = i + 1;
				break;
			}
		}

		return filePath.substring(begin, len);
	}

	/**
	 * 获取文件后缀
	 * @param path 文件路径
	 * @return 后缀
	 */
	public static String getSuffix(String path) {
		if (TUtils.isEmpty(path)) {
			return "";
		}
		return path.substring(path.lastIndexOf(".") + 1);
	}

	/**
	 * 是否为Windows或者Linux（Unix）文件分隔符<br>
	 * Windows平台下分隔符为\，Linux（Unix）为/
	 * @param c 字符
	 * @return 是否为Windows或者Linux（Unix）文件分隔符
	 */
	public static boolean isFileSeparator(char c) {
		return SLASH == c || BACKSLASH == c;
	}

	/**
	 * 下载文件名重新编码
	 * @param response 响应对象
	 * @param realFileName 真实文件名
	 * @return
	 */
	public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName)
			throws UnsupportedEncodingException {
		String percentEncodedFileName = percentEncode(realFileName);

		String contentDispositionValue = "attachment; filename=" + percentEncodedFileName + ";" + "filename*="
				+ "utf-8''" + percentEncodedFileName;
		response.setHeader("Content-disposition", contentDispositionValue);
	}

	/**
	 * 百分号编码工具方法
	 * @param s 需要百分号编码的字符串
	 * @return 百分号编码后的字符串
	 */
	public static String percentEncode(String s) throws UnsupportedEncodingException {
		String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
		return encode.replaceAll("\\+", "%20");
	}

	/**
	 * 生成默认使用的上传路径
	 * @param originalFilename 原文件名称
	 * @return 上传路径 2022/06/05/1654356928000.jpg
	 */
	public static String defaultUploadPath(String originalFilename) {
		return DateUtils.datePath() + "/" + System.currentTimeMillis() + "." + getSuffix(originalFilename);
	}

}
