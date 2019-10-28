package com.example.jsontest;

public class State {
    String location;
    String temp;
    String weather;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public State(String location, String temp, String weather) {
        this.location = location;
        this.temp = temp;
        this.weather = weather;

    }
}

