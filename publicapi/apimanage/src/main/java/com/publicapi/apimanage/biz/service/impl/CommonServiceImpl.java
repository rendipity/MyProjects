package com.publicapi.apimanage.biz.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.VoidResult;
import com.publicapi.apimanage.biz.service.CommonService;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;

@Slf4j
public class CommonServiceImpl implements CommonService {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    // 从环境变量中获取RAM用户的访问密钥（AccessKeyId和AccessKeySecret）
    String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
    String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
    // Bucket名称。
    String bucketName = "lijie-openapi";

    // 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
   public void createBucket(){
           // 创建存储空间。
           if (ossClient.doesBucketExist(bucketName)) {
               log.info("bucket:"+ bucketName+" has exist!");
               // 查看bucket的地区
               String bucketLocation = ossClient.getBucketLocation(bucketName);
               System.out.println("bucketLocation: "+bucketLocation);
           }else {
               Bucket bucket = ossClient.createBucket(bucketName);
               System.out.println("bucket:"+ bucketName+" 创建成功!");
           }
   }

    @Override
    public void upload() {
        String objectName = "uploadTest";
        String content = "Hello OSS";
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
        putObjectResult.getETag();
        putObjectResult.getRequestId();
    }

    @Override
    public void deleteObject() {
        String objectName = "uploadTest";
        ossClient.deleteObject(bucketName, objectName);
    }
}
