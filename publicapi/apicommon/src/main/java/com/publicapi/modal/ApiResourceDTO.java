package com.publicapi.modal;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName api_resource
 */
@Data
public class ApiResourceDTO implements Serializable {

    private static final long serialVersionUID = 1L;
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
    private List<ApiParamsDTO> requestParams;

    /**
     *响应参数
     */
    private List<ApiParamsDTO> responseParams;

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