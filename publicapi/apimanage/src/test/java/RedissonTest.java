import com.publicapi.apimanage.ApiManageApplicationStarter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = ApiManageApplicationStarter.class)
@RunWith(SpringRunner.class)
public class RedissonTest {

    @Resource
    private RedissonClient redissonClient;
    @Test
    public void redissonTest(){
        RBucket<Object> bucket = redissonClient.getBucket("test_ch");
        bucket.set("测试",10, TimeUnit.MINUTES);
        Object response = bucket.get();
        System.out.println(response);
    }
    @Test
    public void unionTest(){
        RScoredSortedSet<Object> scoredSortedSet = redissonClient.getScoredSortedSet("publicapi:apiManager:ApiService:RankList:invokeTime:week:2023/08/35");
        scoredSortedSet.union(RScoredSortedSet.Aggregate.SUM,"publicapi:apiManager:ApiService:RankList:invokeTime:2023/08/28","publicapi:apiManager:ApiService:RankList:invokeTime:2023/08/29");
        scoredSortedSet.forEach(System.out::println);
    }
}
