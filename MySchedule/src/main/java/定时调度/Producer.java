package 定时调度;

import java.text.SimpleDateFormat;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Producer {
    public void start() {
        ScheduleX scheduleX = ScheduleX.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            long currentTime = System.currentTimeMillis();
            long scheduleTime = currentTime + random.nextInt(20) * 1000;
            String task = "任务" + (i + 1);
            scheduleX.register(scheduleTime, task);
            System.out.println("producer register " + task + "，schedule time :" + dateFormat.format(scheduleTime) + " at " + dateFormat.format(currentTime));
        }
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
