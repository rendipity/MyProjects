package com.publicapi.modal.api;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName api_params
 */
@Data
public class ApiParamsDTO implements Serializable {

    private static final long serialVersionUID = 1L;
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