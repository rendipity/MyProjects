package reactor.observer.pull;


import java.util.Observable;

public class Weather1 extends Observable {
    private Integer temperature;

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
        this.setChanged();
        this.notifyObservers();
    }
}
