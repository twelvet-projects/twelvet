package com.twelvet.server.dfs.service.impl;

import com.twelvet.server.dfs.config.MinioConfig;
import com.twelvet.server.dfs.service.ISysFileService;
import com.twelvet.server.dfs.utils.DFSUploadUtils;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioSysFileServiceImpl implements ISysFileService {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient client;


    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        {
            String fileName = DFSUploadUtils.extractFilename(file);
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .stream(file.getInputStream(),file.getSize(),-1)
                    .contentType(file.getContentType())
                    .build();
            client.putObject(args);
            return minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" +fileName;


        }
    }
}
