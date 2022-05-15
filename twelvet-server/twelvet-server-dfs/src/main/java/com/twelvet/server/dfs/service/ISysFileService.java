package com.twelvet.server.dfs.service;

import com.twelvet.framework.utils.DateUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.framework.utils.file.MimeTypeUtils;
import com.twelvet.server.dfs.exception.FileNameLengthLimitExceededException;
import com.twelvet.server.dfs.exception.InvalidExtensionException;
import com.twelvet.server.dfs.utils.DFSUploadUtils;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static com.twelvet.server.dfs.utils.DFSUploadUtils.assertAllowed;
import static com.twelvet.server.dfs.utils.DFSUploadUtils.getExtension;

/**
 * 【DFS】 = Distributed file system 比 【Sys File】 名称要容易理解
 * 文件上传接口
 * 1: default: 最原始的java文件上传
 * 2: ftp 使用ftp模拟文件服务器； 如：iis、linux ftp、vsftpd、FileZilla Server，需要自己搭建服务
 * 3: FastDfs 是淘宝开源的分布式文件系统； 淘宝 开发的分布式 dfs, 需要自己搭建服务 (FastDFS)
 * 4: minio 轻量级分布式文件系统； 类似一个阿里云oss、腾讯COS的一个开源、轻量级别的对象存储付;
 * 5: aliyun oss；  aliyun oss  https://help.aliyun.com/learn/learningpath/oss.html ,需要购买
 * 6: CEPH 分布式大数据文件存储系统 http://docs.ceph.org.cn/
 * @author ruoyi
 */
public interface ISysFileService {

    /**
     * 允许上传文件存放的目录
     * 不同项目，这里可能做不同的修改；不过不想区分，就default;
     * 项目稍微大一些，如果不区分目录，后期要做删除 or 迁移就很麻烦；
     */
    String[] DEFAULT_MODULES_NAME = {
            "default","banner","product","images","music",
            "pdf"
    };



    enum DfsTypeEnum{
        /**
         * 最原始,文件移动
         * @see LocalSysFileServiceImpl
         * 2014-07-06
         */
        LOCAL_FILE,
        /**
         * ftp, 如：iis、linux ftp、vsftpd、FileZilla Server，需要自己搭建服务
         * 这里的FTP要求和主项目在同一个服务器，并且访问路径完全同DEFAULT ,只是DEFAULT: tomcat处理、ftp: 使用 其他ftp工具处理
         * @see FtpFileServiceImpl
         * 「「如果使用tomcat,tomcat自己值提供上传能力，不提供访问能力，访问能力比较危险。我们使用nginx 进行代理访问」」
         * 2019-07-06
         */
        FTP,
        /**
         * 淘宝 开发的分布式 dfs, 需要自己搭建服务 (FastDFS)
         * @see FastDfsSysFileServiceImpl
         * 2016-09-07
         */
        FASTDFS,
        /**
         * aliyun oss  https://help.aliyun.com/learn/learningpath/oss.html ,需要购买
         * 【sts】 【临时授权】 -- 对象存储- 使用签名URL进行临时授权 https://help.aliyun.com/document_detail/32016.html?spm=a2c4g.11186623.6.992.7a943b4aPjkyTA#title-pu8-5o8-x7j
         * @see AliyunOssDsfServiceImpl
         * 2019-08-06
         */
        ALIYUN_OSS,
        /**
         * CEPH 分布式大数据文件存储系统 http://docs.ceph.org.cn/
         * @see CephSysFileServiceImpl
         * 2020-05-06
         */
        CEPH,
        /**
         * minio 类似一个阿里云oss、腾讯COS的一个开源、轻量级别的对象存储付;
         * 英文地址：https://min.io/
         * 中文地址：http://docs.minio.org.cn/docs/     http://www.minio.org.cn/
         * 支持对url进行鉴权：【sts】【临时授权】 【Presigned presignedGetObject 预签】 MinIO STS快速入门指南 http://docs.minio.org.cn/docs/master/minio-sts-quickstart-guide
         * minio SDKS Java Client API参考文档 http://docs.minio.org.cn/docs/master/java-client-api-reference
         * @see MinioSysFileServiceImpl
         * 2021-02-09
         */
        MINIO,
        /**
         * 腾讯云存储 cos
         * @see TencentCosServiceImpl
         */
        TENCENT_COS,
        /**
         * 七牛 Kodo
         * @see QiniuSysFileServiceImpl
         */
        QINIU_KODO
    }


    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    String uploadFile(MultipartFile file) throws InvalidExtensionException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    String uploadFile(MultipartFile file,String modules) throws InvalidExtensionException, ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;


    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址,全路径或者不是全路径都可以
     * @return
     */
    boolean deleteFile(String fileUrl);

    /**
     * 对访问url进行签名加密 别名 【临时安全凭证】
     * 兼容 AWS Security Token Service (STS) 的联合身份临时安全凭证 (federation token) ，更多详细信息请查阅
     *
     * 1、aliyun oss 叫做；STS服务 临时安全令牌（Security Token Service，STS） 说明：https://help.aliyun.com/document_detail/28761.html?spm=a2c4g.11186623.6.880.22bd2fe5pL1d39
     * 2、minio 叫做；resignedGetObject 临时安全令牌（Security Token Service，STS）; 【Presigned presignedGetObject 预签】
     *    http://docs.minio.org.cn/docs/master/minio-sts-quickstart-guide
     *    minio SDKS Java Client API参考文档 http://docs.minio.org.cn/docs/master/java-client-api-reference
     * 3、qiniu ；七牛云存储； 下载凭证(如果Bucket设置成私有，必须要有 下载凭证)，路径：【对象存储==》使用指南===》安全机制===》 下载凭证】 https://developer.qiniu.com/kodo/1202/download-token
     *   https://developer.qiniu.com/kodo/5914/s3-compatible-sts
     * 4、腾讯 临时密钥（临时访问凭证） GetFederationToken 临时密钥生成及使用指引 https://cloud.tencent.com/document/product/436/14048?from=10680
     * 5、fastdfs 防掉链 前提，需要在 fastdfs上面配置 https://www.cnblogs.com/xiaolinstudy/p/9341779.html
     * @param fileUrl 文件访问地址,全路径或者不是全路径都可以
     * @return 返回签名后的url
     */
    String presignedUrl(String fileUrl);

    /**
     * 获取文件占用空间
     * 别名：objectsCapacity
     * @return 文件大小字符串，eg: 100MB、2G； 形如：总 233.57 GB， 可用 72.12 GB
     */
    String objectsCapacityStr();

    default void validateModule(MultipartFile file,String modules) throws InvalidExtensionException {
        Objects.requireNonNull(file,"文件不能为空");
        modules = StringUtils.defaultString(modules,"defaule");

        int fileNamelength = file.getOriginalFilename().length();
        if(fileNamelength > DFSUploadUtils.DEFAULT_FILE_NAME_LENGTH){
            throw new FileNameLengthLimitExceededException(DFSUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
    }


    /**
     * @return 获取文件名称，包含斜杠 /； 形如：/2021/07/17/a77f6bb0-7b0a-4ef1-a839-f8e8aca469b8.jpeg
     */
    default String extractFileName(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String extrnsion = getExtension(file);
        fileName = DateUtils.datePath();
        return fileName;
    }



}
