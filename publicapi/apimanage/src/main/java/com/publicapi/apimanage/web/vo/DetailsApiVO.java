package com.publicapi.apimanage.web.vo;

import lombok.Data;

import java.util.List;

@Data
public class DetailsApiVO {

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
    private String uri;

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
     * 请求参数
     */
    private List<ApiParamsVO> requestParams;

    /**
     * 响应参数
     */
    private List<ApiParamsVO> responseParams;
}
