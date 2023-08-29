package com.publicapi.apimanage.web.controller;

import com.publicapi.apimanage.biz.constants.RoleEnum;
import com.publicapi.apimanage.biz.service.StatisticService;
import com.publicapi.apimanage.common.qto.RankListQuery;
import com.publicapi.apimanage.web.exception.AccessControl;
import com.publicapi.apimanage.web.vo.statistic.RankListVO;
import com.publicapi.modal.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class StatisticController {


    @Resource
    private StatisticService service;

    // 调用排行榜 日榜、周榜、总榜
    @PostMapping ("/rankList")
    @AccessControl(RoleEnum.ADMIN)
    public Result<List<RankListVO>> getRankList(@RequestBody RankListQuery rankListQuery){
        if (rankListQuery.getType().equals("DAY")){
            return Result.success(service.getDayRankList(rankListQuery));
        }
        else if (rankListQuery.getType().equals("WEEK")){
            return Result.success(service.getWeekRankList(rankListQuery));
        }
        return Result.success(service.getTotalRankList(rankListQuery));
    }
}
