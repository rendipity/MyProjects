package com.publicapi.apimanage.biz.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName api_resource
 */
@Data
public class ApiResource {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String code;

    /**
     * 
     */
    private String name;

    /**
     *
     */
    private String description;
    /**
     * 
     */
    private String httpMethod;

    /**
     * 
     */
    private String protocol;

    /**
     * 
     */
    private String host;

    /**
     * 
     */
    private String path;

    /**
     *
     */
    private String groupCode;

    /**
     *
     */
    private Integer callFrequency;
    /**
     * 
     */
    private String requestDemo;

    /**
     * 
     */
    private String responseDemo;

    /**
     *请求参数
     */
    private List<ApiParams> requestParams;

    /**
     *响应参数
     */
    private List<ApiParams> responseParams;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String creator;

    /**
     * 
     */
    private String status;
}