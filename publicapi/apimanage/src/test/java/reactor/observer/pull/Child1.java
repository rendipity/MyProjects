package reactor.observer.pull;

import java.util.Observable;
import java.util.Observer;

public class Child1 implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("观察的数据改变了");
        System.out.println(o);
        System.out.println(arg);
        System.out.println("变化后的数据: "+((Weather1)o).getTemperature());
    }

    public static void main(String[] args) {
        Weather1 weather1 = new Weather1();
        weather1.addObserver(new Child1());
        weather1.setTemperature(20);
    }
}
