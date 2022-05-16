package com.twelvet.server.dfs.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * Minio 配置信息
 *
 * @author twelvet
 * @see MinioSysFileServiceImpl 实现
 */
@RefreshScope
@Component
@Configuration
@ConfigurationProperties(prefix = MinioConfig.PREFIX)
public class MinioConfig {

    public static final String PREFIX = "file.minio";

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }

    /**
     * 服务地址url 或者叫做 endpoint 或者叫做 对象存储服务的URL
     * eg: http://192.168.254.100:9900
     */
    private String url;

    /**
     * 用户名
     * eg: D998GE6ZTQXSATTJWX35
     */
    private String accessKey;

    /**
     * 密码
     * eg: QZVQGnhIQQE734UYSUFlGOZViE6+ZlDEfUG3NjXJ
     */
    private String secretKey;

    /**
     * 存储桶名称
     * eg: mall
     */
    private String bucketName;

    /**
     * 访问域名; url经常是内网地址，外部访问用域名或者外网ip
     * eg: https://cloud.twelvet.cn/dfs
     *
     *  注意！！：minio要配置 Bucket Policy： Bucket ==> Edit Bucket
     *  1、Read Only
     *  2、Write Only
     *  3、Read and Write
     *  4、不做任何分配 【必须要加签之后才能访问】
     */
    private String domain;

    /**
     * 过期时间
     * 文档：MinIO STS快速入门指南 http://docs.minio.org.cn/docs/master/minio-sts-quickstart-guide
     * 文档：适用于与Amazon S3兼容的云存储的MinIO Java SDK: API文档: Presigned操作: presignedGetObject:  http://docs.minio.org.cn/docs/master/java-client-quickstart-guide
     * 默认7天，单位秒；
     * 1小时：3600 = 60 * 60 * 1
     * 24小时（1天）：86400 = 60 * 60 * 24
     * 7天：604800 = 86400 * 7
     * -1： 就永不过期，原样返回url
     */
    private Integer expiryDuration = 86400;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getExpiryDuration() {
        if (expiryDuration == null) {
            // 默认一个小时, 3600秒
            expiryDuration = 86400;
        }
        if (expiryDuration < 1L && expiryDuration != -1) {
            // 最小1秒
            // 如果要永不过期，就不要调用 -1； 直接原样返回
            expiryDuration = 1;
        }
        return expiryDuration;
    }

    public void setExpiryDuration(Integer expiryDuration) {
        this.expiryDuration = expiryDuration;
    }
}
