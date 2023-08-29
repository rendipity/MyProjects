package com.publicapi.apimanage.biz.service;

import com.publicapi.apimanage.common.qto.RankListQuery;
import com.publicapi.apimanage.web.vo.statistic.RankListVO;

import java.util.List;

public interface StatisticService {

    /**
     * 日榜 实时更新
     * @param rankListQuery
     * @return
     */
    List<RankListVO>  getDayRankList(RankListQuery rankListQuery);

    /**
     * 周榜 不包括今天
     * @param rankListQuery
     * @return
     */
    List<RankListVO>  getWeekRankList(RankListQuery rankListQuery);

    /**
     * 总榜 不包括今天
     * @param rankListQuery
     * @return
     */
    List<RankListVO>  getTotalRankList(RankListQuery rankListQuery);

}
