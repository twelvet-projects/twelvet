package com.twelvet.server.dfs.controller;

import com.twelvet.api.dfs.domain.SysDfs;
import com.twelvet.framework.core.application.controller.TWTController;
import com.twelvet.framework.core.application.domain.AjaxResult;
import com.twelvet.framework.jdbc.web.utils.PageUtils;
import com.twelvet.framework.log.annotation.Log;
import com.twelvet.framework.log.enums.BusinessType;
import com.twelvet.server.dfs.service.IDFSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 文件请求处理
 */
@RestController
public class DFSController extends TWTController {

    @Autowired
    private IDFSService sysFileService;

    /**
     * 域名或本机访问地址
     */
    @Value("${fdfs.domain}")
    public String domain;

    /**
     * 多文件上传
     *
     * @param files MultipartFile[]
     * @return R<SysFile>
     */
    @Log(service = "多文件上传", businessType = BusinessType.IMPORT)
    @PostMapping("/batchUpload")
    public AjaxResult batchUpload(MultipartFile[] files) {
        // 上传并返回访问地址
        List<SysDfs> sysDfsList = sysFileService.uploadFiles(files);

        return AjaxResult.success(sysDfsList);
    }

    /**
     * 单文件上传
     *
     * @param file MultipartFile
     * @return R<SysFile>
     */
    @PostMapping("/commonUpload")
    @Log(service = "单文件上传", businessType = BusinessType.IMPORT)
    public AjaxResult commonUpload(MultipartFile file) {
        // 上传并返回访问地址
        SysDfs sysDfs = sysFileService.uploadFile(file);
        String url = domain + File.separator + sysDfs.getPath();
        return AjaxResult.success("上传成功", url);
    }

    /**
     * 删除文件
     *
     * @param fileIds 文件地址
     * @return AjaxResult
     */
    @PreAuthorize("@role.hasPermi('dfs:dfs:remove')")
    @Log(service = "删除文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fileIds}")
    public AjaxResult deleteFile(@PathVariable Long[] fileIds) {
        sysFileService.deleteFile(fileIds);
        return AjaxResult.success();
    }

    /**
     * 分页查询
     *
     * @param sysDfs SysDfs
     * @return AjaxResult
     */
    @PreAuthorize("@role.hasPermi('dfs:dfs:list')")
    @GetMapping("/pageQuery")
    public AjaxResult pageQuery(SysDfs sysDfs) {
        PageUtils.startPage();
        List<SysDfs> sysDfsList = sysFileService.selectSysDfsList(sysDfs);
        return AjaxResult.success(PageUtils.getDataTable(sysDfsList));
    }

}
