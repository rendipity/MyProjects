package com.publicapi.apimanage.web.vo.statistic;

import lombok.Data;

@Data
public class RankListVO {
    /**
     * api编码
     */
    private String apiCode;
    /**
     * api名称
     */
    private String apiName;
    /**
     * 排名
     */
    private Integer rank;
    /**
     * 调用次数
     */
    private Integer invokeTimes;
}
