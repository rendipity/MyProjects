import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

public class GuavaTest {

    @Test
    public void guavaTest(){
        RateLimiter limiter = RateLimiter.create(10);
    }
}
