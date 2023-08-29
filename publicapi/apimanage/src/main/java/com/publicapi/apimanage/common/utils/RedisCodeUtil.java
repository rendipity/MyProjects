package com.publicapi.apimanage.common.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RedisCodeUtil {
    public static String generateRankListKey(String rankListCode, String formattedDate){
        String Biz =  "publicapi";
        String App = "apiManager";
        String className = "ApiService";
        String usage = "RankList";
        return Biz+":"+App+":"+className+":"+usage+":"+rankListCode+":"+ formattedDate;
    }

    public static List<String> getWeekRedisCode(String rankListCode, Date date){
        List<String> resultDate = new ArrayList<>();
        for (int i = 1; i <=7 ; i++) {
            DateTime weekDate = DateUtil.offsetDay(date, -i);
            String formattedDate = DateUtil.format(weekDate, "yyyy/MM/dd");
            resultDate.add(generateRankListKey(rankListCode,formattedDate));
        }
        return resultDate;
    }
    public static String generateWeekRankListKey(String rankListCode, String formattedDate){
        String Biz =  "publicapi";
        String App = "apiManager";
        String className = "ApiService";
        String usage = "RankList";
        return Biz+":"+App+":"+className+":"+usage+":"+"week"+":"+rankListCode+":" +formattedDate;
    }
    public static String generateTotalRankListKey(String rankListCode, String formattedDate){
        String Biz =  "publicapi";
        String App = "apiManager";
        String className = "ApiService";
        String usage = "RankList";
        return Biz+":"+App+":"+className+":"+usage+":"+"total"+":"+rankListCode+":" +formattedDate;
    }

}
