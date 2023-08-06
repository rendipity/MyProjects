package reactor.observer.push;

import java.util.Observable;
import java.util.Observer;

public class Child implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("观察的数据改变了");
        System.out.println(o);
        System.out.println("变化后的数据: "+arg);
    }

    public static void main(String[] args) {
        Weather weather = new Weather();
        weather.addObserver(new Child());
        weather.setTemperature(20);
    }
}
