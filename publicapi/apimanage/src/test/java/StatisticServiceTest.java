import com.publicapi.apimanage.ApiManageApplicationStarter;
import com.publicapi.apimanage.biz.service.StatisticService;
import com.publicapi.apimanage.common.qto.RankListQuery;
import com.publicapi.apimanage.web.vo.statistic.RankListVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static com.publicapi.apimanage.biz.constants.ApiManageConstants.INVOKE_TIME_RANK_CODE;

@SpringBootTest(classes = ApiManageApplicationStarter.class)
@RunWith(SpringRunner.class)
public class StatisticServiceTest {
    
    @Resource
    private StatisticService service; 
    @Test
    public void getDayRankList(){
        RankListQuery rankListQuery = new RankListQuery();
        rankListQuery.setRankListCode(INVOKE_TIME_RANK_CODE);
        rankListQuery.setFrom(0);
        List<RankListVO> dayRankList = service.getDayRankList(rankListQuery);
        dayRankList.stream().forEach(System.out::println);
    }

    @Test
    public void getWeekRankList(){
        RankListQuery rankListQuery = new RankListQuery();
        rankListQuery.setRankListCode(INVOKE_TIME_RANK_CODE);
        List<RankListVO> weekRankList = service.getWeekRankList(rankListQuery);
        weekRankList.stream().forEach(System.out::println);
    }

    @Test
    public void getTotalRankList(){
        RankListQuery rankListQuery = new RankListQuery();
        rankListQuery.setRankListCode(INVOKE_TIME_RANK_CODE);
        List<RankListVO> totalRankList = service.getTotalRankList(rankListQuery);
        totalRankList.stream().forEach(System.out::println);
    }
}
