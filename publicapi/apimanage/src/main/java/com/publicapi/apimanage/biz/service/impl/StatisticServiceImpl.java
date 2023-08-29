package com.publicapi.apimanage.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.publicapi.apimanage.biz.bo.ApiResource;
import com.publicapi.apimanage.biz.service.ApiService;
import com.publicapi.apimanage.biz.service.StatisticService;
import com.publicapi.apimanage.common.qto.RankListQuery;
import com.publicapi.apimanage.common.utils.RedisCodeUtil;
import com.publicapi.apimanage.web.vo.statistic.RankListVO;
import org.redisson.api.*;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class StatisticServiceImpl implements StatisticService {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private ApiService apiService;
    @Override
    public List<RankListVO> getDayRankList(RankListQuery rankListQuery) {
        String formattedDate = DateUtil.format(DateUtil.date(), "yyyy/MM/dd");
        RScoredSortedSet<Object> scoredSortedSet = redissonClient.getScoredSortedSet(RedisCodeUtil.generateRankListKey(rankListQuery.getRankListCode(),formattedDate));
        List<ScoredEntry> scoredEntries = (ArrayList)scoredSortedSet.entryRangeReversed(rankListQuery.getFrom(), rankListQuery.getEnd());
        return entry2VO(scoredEntries);
    }

    @Override
    public List<RankListVO> getWeekRankList(RankListQuery rankListQuery) {
        // 查看是否有今天生成的周榜
        String formattedDate = DateUtil.format(DateUtil.date(), "yyyy/MM/dd");
        String weekRedisCode = RedisCodeUtil.generateWeekRankListKey(rankListQuery.getRankListCode(), formattedDate);
        RScoredSortedSet<Object> scoredSortedSet = redissonClient.getScoredSortedSet(weekRedisCode);
        if (scoredSortedSet.isEmpty()){
            // 获取一个周的key
            List<String> redisCodes = RedisCodeUtil.getWeekRedisCode(rankListQuery.getRankListCode(), DateUtil.date());
            // 将上述key取并集
            RBatch batch = redissonClient.createBatch();
            batch.getScoredSortedSet(weekRedisCode).unionAsync(RScoredSortedSet.Aggregate.SUM,redisCodes.toArray(new String[0]));
            batch.getScoredSortedSet(weekRedisCode).expireAsync(Instant.now().plusSeconds(TimeUnit.DAYS.toSeconds(1)));
            batch.execute();
        }
        List<ScoredEntry> scoredEntries = (ArrayList)scoredSortedSet.entryRangeReversed(rankListQuery.getFrom(), rankListQuery.getEnd());
        return entry2VO(scoredEntries);
    }
    @Override
    public List<RankListVO> getTotalRankList(RankListQuery rankListQuery) {
        String formattedDate = DateUtil.format(DateUtil.date(), "yyyy/MM/dd");
        String totalRedisCode = RedisCodeUtil.generateTotalRankListKey(rankListQuery.getRankListCode(), formattedDate);
        RScoredSortedSet<Object> scoredSortedSet = redissonClient.getScoredSortedSet(totalRedisCode);
        if (scoredSortedSet.isEmpty()){
            // 获取所有日期的key
            String keyPattern = RedisCodeUtil.generateRankListKey(rankListQuery.getRankListCode(), "*");
            RKeys keys = redissonClient.getKeys();
            Iterable<String> rankListKeyIterable = keys.getKeysByPattern(keyPattern);
            List<String> rankListKeys = new ArrayList<>();
            // 今日排行榜key
            String todayKey = RedisCodeUtil.generateRankListKey(rankListQuery.getRankListCode(), formattedDate);
            rankListKeyIterable.forEach(key->{
                // 总榜不包括今天
                if (!todayKey.equals(key)) {
                    rankListKeys.add(key);
                }
            });
            RBatch batch = redissonClient.createBatch();
            batch.getScoredSortedSet(totalRedisCode).unionAsync(RScoredSortedSet.Aggregate.SUM,rankListKeys.toArray(new String[0]));
            batch.getScoredSortedSet(totalRedisCode).expireAsync(Instant.now().plusSeconds(TimeUnit.DAYS.toSeconds(1)));
            batch.execute();
        }
        List<ScoredEntry> scoredEntries = (ArrayList)scoredSortedSet.entryRangeReversed(rankListQuery.getFrom(), rankListQuery.getEnd());
        return entry2VO(scoredEntries);
    }

    /**
     * 将排行榜的entryList转化为VOList
     * @param scoredEntries 排行榜键值对
     * @return 排行榜VO
     */
    private List<RankListVO>  entry2VO(List<ScoredEntry> scoredEntries){
        List<String> apiCodes = new ArrayList<>();
        List<RankListVO> result = new ArrayList<>();
        for (int i =1; i <= scoredEntries.size(); i++){
            ScoredEntry entry = scoredEntries.get(i - 1);
            RankListVO rankListVO = new RankListVO();
            rankListVO.setRank(i);
            rankListVO.setApiCode((String)entry.getValue());
            rankListVO.setInvokeTimes(entry.getScore().intValue());
            result.add(rankListVO);
            apiCodes.add((String)entry.getValue());
        }
        // 批量查询api
        Map<String, ApiResource> apiResourceMap = apiService.listApi(apiCodes).stream().collect(Collectors.toMap(ApiResource::getCode, Function.identity()));
        result = result.stream().peek(vo ->
                        vo.setApiName(apiResourceMap.get(vo.getApiCode()).getName()))
                .filter(vo -> StrUtil.isNotEmpty(vo.getApiName()))
                .collect(Collectors.toList());
        return result;
    }
}
