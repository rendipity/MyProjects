package com.publicapi.apimanage.common.qto;

import lombok.Data;

@Data
public class RankListQuery {

    /**
     * 排行榜code  调用次数排行榜 invokeTime
     */
    private String rankListCode;

    /**
     * 排行榜类型 日榜 DAY、周榜 WEEK、总榜 TOTAL
     */
    private String type;

    /**
     * 起始排名 默认 0
     */
    private Integer from = 0;

    /**
     * 终止排名 默认 -1
     */
    private Integer end =-1;

}
