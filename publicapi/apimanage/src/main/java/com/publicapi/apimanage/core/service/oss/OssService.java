package com.publicapi.apimanage.core.service.oss;

public interface OssService {

    /**
     * @param name 文件名
     * @param data 输入流
     * @return 文件访问路径
     */
    String putFile(String name, byte[] data);
}
