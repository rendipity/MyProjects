package com.mySpring.reader.Object;

public class Student {
    private String name;
    private Car car;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", car=" + car +
                '}';
    }
}
