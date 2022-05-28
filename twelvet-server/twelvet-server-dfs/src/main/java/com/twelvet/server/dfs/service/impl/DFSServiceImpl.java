package com.twelvet.server.dfs.service.impl;

import com.mysql.cj.protocol.WatchableOutputStream;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.twelvet.api.dfs.domain.SysDfs;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.utils.$;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.file.FileUtils;
import com.twelvet.framework.utils.http.ServletUtils;
import com.twelvet.server.dfs.Util.StringUtil;
import com.twelvet.server.dfs.config.OSSConfig;
import com.twelvet.server.dfs.mapper.DFSMapper;
import com.twelvet.server.dfs.service.IDFSService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private static final Logger logger = LoggerFactory.getLogger(DFSServiceImpl.class);

    @Autowired
    private OSSConfig ossConfig;

    private UploadManager uploadManager;

    private String token;

    private Auth auth;

    private BucketManager bucketManager;

    @Autowired
    private DFSMapper dfsMapper;

    @PostConstruct
    public void init() {
        uploadManager = new UploadManager(new Configuration(Zone.zone2()));

        auth = Auth.create(ossConfig.getAccessKey(), ossConfig.getSecretKey());

        bucketManager = new BucketManager(auth, new Configuration(Zone.zone2()));

        token = auth.uploadToken(ossConfig.getBucketName());
    }

    /**
     * FastDfs多文件文件上传接口
     *
     * @param files 上传的文件
     * @return 访问地址
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<SysDfs> uploadFiles(MultipartFile[] files) {


        List<SysDfs> fileDfs = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();


                String imgName = StringUtil.getRandomImgName(fileName);


                InputStream inputStream = file.getInputStream();

                Response res = uploadManager.put(inputStream, imgName, token, null, null);

                if (!res.isOK()) {
                    throw new RuntimeException("出错啦：" + res.toString());
                }

                DefaultPutRet putRet = JacksonUtils.readValue(res.bodyString(), DefaultPutRet.class);


                SysDfs sysDfs = new SysDfs();

                long size = file.getSize();

                sysDfs.setSize(size);
                sysDfs.setPath("/" + imgName);
                sysDfs.setType(FileUtils.getSuffix(imgName));
                sysDfs.setFileName(FileUtils.getName(imgName));
                sysDfs.setOriginalFileName(fileName);
                sysDfs.setSpaceName("dev");


                fileDfs.add(sysDfs);
                // 插入数据库

            }
            dfsMapper.batchSysDfs(fileDfs);

            return fileDfs;

        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());

        } catch (IOException e) {

            throw new TWTException("文件上传异常");
        }

        return null;

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

            String fileName = file.getOriginalFilename();


            String imgName = StringUtil.getRandomImgName(fileName);


            InputStream inputStream = file.getInputStream();

            Response res = uploadManager.put(inputStream, imgName, token, null, null);

            if (!res.isOK()) {
                throw new RuntimeException("出错啦：" + res.toString());
            }

            DefaultPutRet putRet = JacksonUtils.readValue(res.bodyString(), DefaultPutRet.class);


            SysDfs sysDfs = new SysDfs();

            long size = file.getSize();

            sysDfs.setSize(size);
            sysDfs.setPath("/" + imgName);
            sysDfs.setType(FileUtils.getSuffix(imgName));
            sysDfs.setFileName(FileUtils.getName(imgName));
            sysDfs.setOriginalFileName(fileName);
            sysDfs.setSpaceName("dev");

            dfsMapper.insertSysDfs(sysDfs);


            return sysDfs;
        } catch (IOException e) {
            throw new TWTException("文件上传异常");
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());

        }
        return null;

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
                String fileName = sysDfs.getFileName();
                bucketManager.delete(ossConfig.getBucketName(), fileName);
            }
            // 删除数据库信息
            dfsMapper.deleteSysDfsByFileIds(fileIds);
        } catch (QiniuException e) {
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
//    @Override
//    public void download(Long fileId) {
//        try {
//            SysDfs sysDfs = dfsMapper.selectDfsByFileIds(fileId);
//            String fileUrl = ossConfig.getYouUrl() + sysDfs.getFileName();
//
//            OkHttpClient client = new OkHttpClient();
//
//            Request req = new Request.Builder().url(fileUrl).build();
//
//            okhttp3.Response resp = null;
//            byte[] bytes = null;
//            resp = client.newCall(req).execute();
//            System.out.println(resp.isSuccessful());
//            if (resp.isSuccessful()) {
//                ResponseBody body = resp.body();
//                InputStream is = body.byteStream();
//                bytes = readInputStream(is);
//            }
//            if ($.isEmpty(bytes)) {
//                throw new TWTException("无法获取文件");
//            }
//
//            ServletUtils.download(bytes, sysDfs.getFileName());
//        } catch (IOException e) {
//            logger.error("获取文件流失败", e);
//            throw new TWTException("获取文件流失败");
//        }
//    }


    private static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream writer = new WatchableOutputStream();
        byte[] buff = new byte[1024 * 2];
        int len = 0;
        try {
            while ((len = is.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            return writer.toByteArray();
        } catch (IOException e) {
            logger.error("写入流失败", e);
            throw new TWTException("获取失败");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                logger.error("关闭流失败", e);
            }
        }
    }

}
