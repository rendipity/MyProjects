package com.publicapi.dynamicroute.apimodal;

import lombok.Data;

/**
 * 
 * @TableName api_resource
 */
@Data
public class ApiResource {

    /**
     * api编码
     */
    private String code;

    /**
     * http请求类型 GET、POST
     */
    private String httpMethod;

    /**
     * 协议 目前支持http、https
     */
    private String protocol;

    /**
     * 主机名
     */
    private String host;

    /**
     * url path
     */
    private String path;

    /**
     * 调用频率
     */
    private Integer callFrequency;
}