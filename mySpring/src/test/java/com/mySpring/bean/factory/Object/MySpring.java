package com.mySpring.bean.factory.Object;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MySpring {
    private double version;
    private Date date;
    String msg;

    public void hello(){
        System.out.println("hello,mySpring");
    }


    public MySpring() {
    }

    public MySpring(double version, Date date, String msg) {
        this.version = version;
        this.date = date;
        this.msg = msg;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /*@Override
    public String toString() {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "MySpring{" +
                "version=" + version +
                ", date=" + simpleDateFormat.format(date) +
                ", msg='" + msg + '\'' +
                '}';
    }*/
}
