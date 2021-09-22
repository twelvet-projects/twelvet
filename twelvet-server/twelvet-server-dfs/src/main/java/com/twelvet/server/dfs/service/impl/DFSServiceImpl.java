package com.twelvet.server.dfs.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.twelvet.api.dfs.domain.SysDfs;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.utils.TWTUtils;
import com.twelvet.framework.utils.file.FileUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import com.twelvet.server.dfs.mapper.DFSMapper;
import com.twelvet.server.dfs.service.IDFSService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: FastDFS文件存储
 */
@Primary
@Service
public class DFSServiceImpl implements IDFSService {

    private final Logger logger = LoggerFactory.getLogger(DFSServiceImpl.class);

    @Autowired
    private DFSMapper dfsMapper;

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * FastDfs多文件文件上传接口
     *
     * @param files 上传的文件
     * @return 访问地址
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<SysDfs> uploadFiles(MultipartFile[] files) {

        try {
            List<SysDfs> fileDfs = new ArrayList<>();

            for (MultipartFile file : files) {

                long size = file.getSize();

                StorePath storePath = storageClient.uploadFile(
                        file.getInputStream(), size,
                        FilenameUtils.getExtension(file.getOriginalFilename()),
                        null
                );

                String fullPath = storePath.getFullPath();

                SysDfs sysDfs = new SysDfs();
                sysDfs.setSize(size);
                sysDfs.setPath(fullPath);
                sysDfs.setType(FileUtils.getSuffix(fullPath));
                sysDfs.setFileName(FileUtils.getName(fullPath));
                sysDfs.setOriginalFileName(file.getOriginalFilename());
                sysDfs.setSpaceName(storePath.getGroup());
                fileDfs.add(sysDfs);
            }

            // 插入数据库
            dfsMapper.batchSysDfs(fileDfs);

            return fileDfs;
        } catch (IOException e) {
            throw new TWTException("文件上传异常");
        }


    }

    /**
     * FastDfs文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysDfs uploadFile(MultipartFile file) {

        try {

            long size = file.getSize();

            StorePath storePath = storageClient.uploadFile(
                    file.getInputStream(), size,
                    FilenameUtils.getExtension(file.getOriginalFilename()),
                    null
            );

            String fullPath = storePath.getFullPath();

            SysDfs sysDfs = new SysDfs();
            sysDfs.setSize(size);
            sysDfs.setPath(fullPath);
            sysDfs.setType(FileUtils.getSuffix(fullPath));
            sysDfs.setFileName(FileUtils.getName(fullPath));
            sysDfs.setOriginalFileName(file.getOriginalFilename());
            sysDfs.setSpaceName(storePath.getGroup());

            // 插入数据库
            dfsMapper.insertSysDfs(sysDfs);

            return sysDfs;
        } catch (IOException e) {
            throw new TWTException("文件上传异常");
        }

    }

    /**
     * 删除文件
     *
     * @param fileIds 文件地址
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(Long[] fileIds) {
        try {
            List<SysDfs> sysDfsList = dfsMapper.selectDfsListByFileIds(fileIds);
            for (SysDfs sysDfs : sysDfsList) {
                StorePath storePath = StorePath.parseFromUrl(sysDfs.getPath());
                storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            }
            // 删除数据库信息
            dfsMapper.deleteSysDfsByFileIds(fileIds);
        } catch (FdfsUnsupportStorePathException e) {
            logger.error(e.getMessage());
            throw new TWTException("删除文件出错");
        }
    }

    /**
     * 分页查询
     *
     * @param sysDfs SysDfs
     * @return List<SysDfs>
     */
    @Override
    public List<SysDfs> selectUserList(SysDfs sysDfs) {
        return dfsMapper.selectDfsList(sysDfs);
    }

    /**
     * 下载文件导出给予前端
     *
     * @param fileId 文件id
     */
    @Override
    public void download(Long fileId) {
        SysDfs sysDfs = dfsMapper.selectDfsByFileIds(fileId);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = null;
        if (TWTUtils.isNotEmpty(sysDfs)) {
            // 解析路径
            StorePath storePath = StorePath.parseFromUrl(sysDfs.getPath());
            bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), downloadByteArray);
        }

        if (TWTUtils.isEmpty(bytes)) {
            throw new TWTException("无法获取文件");
        }

        ServletUtils.download(bytes, sysDfs.getFileName());

    }

}
