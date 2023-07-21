package com.publicapi.apimanage.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSClientConfig {

    @Bean
    public OSS createOssClient(){
            // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
            String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
            // 从环境变量中获取访问凭证。运行本代码示例之前，请先配置环境变量。
            EnvironmentVariableCredentialsProvider credentialsProvider = null;
            try {
                credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            } catch (ClientException e) {
                throw new RuntimeException(e);
            }
            // 创建ClientBuilderConfiguration。
            // ClientBuilderConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
            ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
            // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
            conf.setMaxConnections(200);
            // 设置Socket层传输数据的超时时间，默认为50000毫秒。
            conf.setSocketTimeout(10000);
            // 设置建立连接的超时时间，默认为50000毫秒。
            conf.setConnectionTimeout(10000);
            // 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
            conf.setConnectionRequestTimeout(1000);
            // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
            conf.setIdleConnectionTime(10000);
            // 设置失败请求重试次数，默认为3次。
            conf.setMaxErrorRetry(5);
            // 设置是否支持将自定义域名作为Endpoint，默认支持。
            conf.setSupportCname(true);
            // 设置是否开启二级域名的访问方式，默认不开启。
            conf.setSLDEnabled(true);
            // 设置连接OSS所使用的协议（HTTP或HTTPS），默认为HTTP。
            conf.setProtocol(Protocol.HTTP);
           /* // 设置用户代理，指HTTP的User-Agent头，默认为aliyun-sdk-java。
            conf.setUserAgent("aliyun-sdk-java");
            // 设置代理服务器端口。
            conf.setProxyHost("<yourProxyHost>");
            // 设置代理服务器验证的用户名。
            conf.setProxyUsername("<yourProxyUserName>");
            // 设置代理服务器验证的密码。
            conf.setProxyPassword("<yourProxyPassword>");*/
            // 设置是否开启HTTP重定向，默认开启。
            conf.setRedirectEnable(true);
            // 设置是否开启SSL证书校验，默认开启。
            conf.setVerifySSLEnable(true);
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider, conf);
            return ossClient;
    }
}
