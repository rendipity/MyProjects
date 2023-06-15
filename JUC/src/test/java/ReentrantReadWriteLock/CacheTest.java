package ReentrantReadWriteLock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    @Resource
    Cache cache;

    @Test
    public void testCache() throws InterruptedException {
        StopWatch stopWatch = new StopWatch("cacheTest");
        stopWatch.start("cache");
        CountDownLatch cacheDownLatch = new CountDownLatch(10);
        for (int k = 0; k < 10; k++) {
            new Thread(()->{
                for (int i = 0; i <1000 ; i++) {
                    cache.queryByIdFromCache(1);
                }
                cacheDownLatch.countDown();
            },"cacheThread-"+k).start();
        }
        cacheDownLatch.await();
        System.out.println("cache finished");
        stopWatch.stop();
        stopWatch.start("db");
        CountDownLatch dbDownLatch = new CountDownLatch(10);
        for (int k = 0; k < 10; k++) {
            new Thread(()->{
                for (int i = 0; i <1000 ; i++) {
                    cache.queryByIdFromDb(1);
                }
                dbDownLatch.countDown();
            },"DbThread-"+k).start();
        }
        dbDownLatch.await();
        System.out.println("db finished");
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
