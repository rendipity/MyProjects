package com.publicapi.apimanage.biz.bo;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName api_dictionary
 */
@Data
public class ApiDictionary  {

    /**
     *
     */
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 字典值
     */
    private String value;

    /**
     * 目标类型 是什么类型字典项 param_type
     */
    private String targettype;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

}