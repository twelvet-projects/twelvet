package com.twelvet.server.dfs.controller.api;

import com.twelvet.api.dfs.domain.SysDfs;
import com.twelvet.api.dfs.domain.SysFile;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.security.annotation.AuthIgnore;
import com.twelvet.framework.utils.file.FileUtils;
import com.twelvet.server.dfs.service.IDFSService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 文件请求处理API
 */
@Hidden
@AuthIgnore
@RestController
@RequestMapping("/api")
public class DFSApi extends TWTController {

	@Autowired
	private IDFSService sysFileService;

	/**
	 * 系统单文件上传API
	 * @param file MultipartFile
	 * @return R<SysFile>
	 */
	@PostMapping("/upload")
	public R<SysFile> upload(MultipartFile file) {
		// 上传并返回访问地址
		SysDfs sysDfs = sysFileService.uploadFile(file);

		String path = sysDfs.getPath();

		SysFile sysFile = new SysFile();
		sysFile.setName(FileUtils.getName(path));
		sysFile.setUrl(path);

		return R.ok(sysFile);
	}

}
