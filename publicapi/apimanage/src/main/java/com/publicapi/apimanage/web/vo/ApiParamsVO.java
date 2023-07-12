package com.publicapi.apimanage.web.vo;

import lombok.Data;

/**
 * 
 * @TableName api_params
 */
@Data
public class ApiParamsVO {

    /**
     * 
     */
    private String paramName;

    /**
     * 
     */
    private String paramType;

    /**
     * 是否必传 1-必传 0-非必传
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

}