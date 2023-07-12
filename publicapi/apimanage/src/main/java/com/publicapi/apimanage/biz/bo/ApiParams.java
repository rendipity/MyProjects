package com.publicapi.apimanage.biz.bo;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName api_params
 */
@Data
public class ApiParams {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String paramName;

    /**
     * 
     */
    private String paramType;

    /**
     * 是否必传
     */
    private Integer required;

    /**
     * 
     */
    private String exampleValue;

    /**
     * 
     */
    private String paramDesc;

    /**
     * 
     */
    private String apiCode;

    /**
     * 
     */
    private String creator;

    /**
     * 
     */
    private Date createTime;

}