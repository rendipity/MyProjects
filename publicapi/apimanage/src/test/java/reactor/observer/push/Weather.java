package reactor.observer.push;


import java.util.Observable;

public class Weather extends Observable {
    private Integer temperature;

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
        this.setChanged();
        this.notifyObservers(temperature);
    }
}
