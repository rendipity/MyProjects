package com.publicapi.apimanage.web.vo.api;

import lombok.Data;

@Data
public class ListApiVO {

    /**
     *api编码
     */
    private String code;
    /**
     * api名称
     */
    private String name;

    /**
     * api 功能描述
     */
    private String description;

    /**
     * 调用频率
     */
    private Integer callFrequency;

}
