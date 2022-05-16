package com.twelvet.server.dfs.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.twelvet.framework.utils.SpringUtils;
import com.twelvet.server.dfs.config.MinioConfig;
import com.twelvet.server.dfs.exception.InvalidExtensionException;
import com.twelvet.server.dfs.service.ISysFileService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class MinioSysFileServiceImpl implements ISysFileService {

    private final MinioConfig minioConfig;
    private final MinioClient minioClient;

    public MinioSysFileServiceImpl(MinioConfig minioConfig, MinioClient minioClient) {
        this.minioConfig = minioConfig;
        this.minioClient = minioClient;
    }


    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws InvalidExtensionException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return this.uploadFile(file, null);
    }

    @Override
    public String uploadFile(MultipartFile file, String modules) throws InvalidExtensionException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        validateModule(file, modules);
        String fileName = StringUtils.defaultString(modules, "defult") + "/" + extractFileName(file);

        boolean isProd = "prod".equalsIgnoreCase(SpringUtil.getActiveProfile());

        if (!isProd) {
            fileName = SpringUtils.getActiveProfile() + "/" + fileName;
        }

        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(fileName)
                .object(fileName)

                //file.getInputStam() 没有这个方法
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);

        return minioConfig.getDomain() + "/" + minioConfig.getBucketName() + "/" + fileName;

    }

    @Override
    public boolean deleteFile(String fileUrl) {

        RemoveObjectArgs arg = RemoveObjectArgs.builder().
                bucket(minioConfig.getBucketName()).
                object(getStorePath(fileUrl)).
                build();

        try {
            minioClient.removeObject(arg);
        } catch (ErrorResponseException |
                InsufficientDataException |
                InternalException |
                InvalidKeyException |
                InvalidResponseException |
                IOException |
                NoSuchAlgorithmException |
                ServerException |
                XmlParserException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public String objectsCapacityStr() {

        AtomicLong atomicLong = new AtomicLong();
        atomicLong.set(0);
        String result = "";

        ListObjectsArgs args = ListObjectsArgs.builder().bucket(minioConfig.getBucketName()).build();
        minioClient.listObjects(args).forEach(
                new Consumer<Result<Item>>() {
                    @Override
                    public void accept(Result<Item> itemResult) {
                        try {
                            atomicLong.addAndGet(itemResult.get().size() / 1024);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        long size = atomicLong.get();
        if (size > (1024 * 1024)) {
            result = (BigDecimal.valueOf((double) size / 1024 / 1024)).setScale(2, RoundingMode.HALF_UP) + "GB";
        } else if (size > 1024) {
            result = (BigDecimal.valueOf((double) size / 1024).setScale(2, RoundingMode.HALF_UP)) + "MB";
        } else {
            result = size + "KB";
        }
        return result;
    }


    @Override
    public String presignedUrl(String fileUrl) {
        if (minioConfig.getExpiryDuration() == -1) {
            return fileUrl;
        }
        String signKey = "?X-Amz-Algorithm=";
        if (fileUrl.contains(signKey)) {
            return fileUrl;
        }
        String objectName = this.getStorePath(fileUrl);
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder().
                bucket(minioConfig.getBucketName()).
                method(Method.GET).
                object(objectName).
                expiry(minioConfig.getExpiryDuration(), TimeUnit.SECONDS).build();

        String presignedObjectUrl = null;
        try {
            presignedObjectUrl = minioClient.getPresignedObjectUrl(args);
            String basePrivateUrl = minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/";
            presignedObjectUrl = presignedObjectUrl.replace(basePrivateUrl, "");
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException | ServerException e) {
            e.printStackTrace();
            presignedObjectUrl = fileUrl;
        }
        return minioConfig.getDomain() + "/" + minioConfig.getBucketName() + "/" + presignedObjectUrl;
    }


    /**
     * 转换url，为原始的key
     *
     * @param filePath https://cloud.twelvet.cn/dfs/dev/default/2021/07/18/f4243eb2-06a1-4304-bdfc-e2964b8721bb.jpeg
     * @return dev/default/2021/07/18/f4243eb2-06a1-4304-bdfc-e2964b8721bb.jpeg
     */
    private String getStorePath(String filePath) {
        String oldPath = filePath;
        // 处理方式1
        String publicPath3 = minioConfig.getDomain() + "/" + minioConfig.getBucketName() + "/";
        filePath = filePath.replace(publicPath3, "");

        if (oldPath.equals(filePath)) {
            // 处理方式2
            String publicPath4 = minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/";
            filePath = filePath.replace(publicPath4, "");
        }
        return filePath;
    }


}

