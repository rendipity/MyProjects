package CountDownLatch;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

public class GameLoading {
    public static void run(int n) throws InterruptedException {
        Random r =new Random();
        String[] loading = new String[n];
        Arrays.fill(loading,"0%");
        CountDownLatch countDownLatch = new CountDownLatch(n);
        for (int i = 0; i < n ; i++) {
            int k =i;
            new Thread(()->{
                for (int j = 0; j < 100;) {
                    int sleepTime=r.nextInt(500);
                    try {
                        sleep((long)(sleepTime+500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int num = r.nextInt(10);
                    j=Math.min(100,j+num);
                    loading[k]=j+"%";
                    System.out.print("\r "+Arrays.toString(loading));
                }
                countDownLatch.countDown();
            },"thread"+i).start();
        }
        countDownLatch.await();
        System.out.println("\ngame begin");
    }

    public static void main(String[] args) throws InterruptedException {
        GameLoading.run(10);
    }
}
