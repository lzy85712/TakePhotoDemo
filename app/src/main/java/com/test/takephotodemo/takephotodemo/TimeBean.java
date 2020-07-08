package com.test.takephotodemo.takephotodemo;

public class TimeBean {

    public String getCountDownTime() {
        return countDownTime;
    }

    public void setCountDownTime(String countDownTime) {
        this.countDownTime = countDownTime;
    }

    public TimeBean(String countDownTime) {
        this.countDownTime = countDownTime;
    }

    private String countDownTime;
}
