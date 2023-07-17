package com.publicapi.apimanage.web.vo.api;

import lombok.Data;

import java.util.List;

@Data
public class AddApiVO {

    /**
     * api名称
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
     * 调用频率
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
