package com.twelvet.server.ai.client;

import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Var;
import com.dtflys.forest.callback.OnProgress;
import com.dtflys.forest.extensions.DownloadFile;

import java.io.File;

/**
 * AI请求客户端
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-05-14
 */
public interface AIClient {

	/**
	 * 下载文件
	 * @param url 请求地址
	 * @param dir 下载目录地址
	 * @param filename 文件名称
	 * @param onProgress 下载进度监听
	 * @return File
	 */
	@Get(url = "${url}")
	@DownloadFile(dir = "${dir}", filename = "${filename}")
	File downloadFile(@Var("url") String url, @Var("dir") String dir, @Var("filename") String filename,
			OnProgress onProgress);

}
