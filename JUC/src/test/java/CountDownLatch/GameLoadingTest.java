package CountDownLatch;

import org.junit.Test;

public class GameLoadingTest {
    @Test
    public void testLoading() throws InterruptedException {
        GameLoading loading = new GameLoading();
        loading.run(10);
    }
}
