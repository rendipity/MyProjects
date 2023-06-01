package 定时调度;

import java.text.SimpleDateFormat;

public class Consumer {
    public void start(){
        ScheduleX scheduleX = ScheduleX.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            for (int i = 0; i < 10; i++) {
                ScheduleX.Node taskNode = scheduleX.get();
                System.out.println(taskNode.task+" schedule time: "+ dateFormat.format(taskNode.time)+
                        " execution time: "+dateFormat.format(System.currentTimeMillis()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
