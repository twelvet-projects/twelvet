package com.twelvet.server.dfs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("oss.qiniu")
public class OSSConfig {

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String youUrl = "http://rc4aqzxrn.hn-bkt.clouddn.com/";


    public String getYouUrl() {
        return youUrl;
    }

    public void setYouUrl(String youUrl) {
        this.youUrl = youUrl;
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
}
