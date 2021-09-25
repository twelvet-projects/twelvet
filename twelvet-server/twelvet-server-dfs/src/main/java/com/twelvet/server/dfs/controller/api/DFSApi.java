package com.twelvet.server.dfs.controller.api;

import com.twelvet.api.dfs.domain.SysDfs;
import com.twelvet.api.dfs.domain.SysFile;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.domain.R;
import com.twelvet.framework.utils.file.FileUtils;
import com.twelvet.server.dfs.service.IDFSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 文件请求处理API
 */
@RestController
@RequestMapping("/api")
public class DFSApi extends TWTController {

    @Autowired
    private IDFSService sysFileService;

    /**
     * 域名或本机访问地址
     */
    @Value("${fdfs.domain}")
    public String domain;

    /**
     * 系统单文件上传API
     *
     * @param file MultipartFile
     * @return R<SysFile>
     */
    @PostMapping("/upload")
    public R<SysFile> upload(MultipartFile file) {
        // 上传并返回访问地址
        SysDfs sysDfs = sysFileService.uploadFile(file);

        String path = sysDfs.getPath();

        String url = domain + File.separator + path;

        SysFile sysFile = new SysFile();
        sysFile.setName(FileUtils.getName(url));
        sysFile.setUrl(url);

        return R.ok(sysFile);
    }

}
