package com.gam.firealarm;

public class Measurement {
    public int id;
    public float temperature;
    public float humidity;
    public float flame;

    public Measurement(){
        this.id = 0;
        this.temperature = 0;
        this.humidity = 0;
        this.flame = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getFlame() {
        return flame;
    }

    public void setFlame(float flame) {
        this.flame = flame;
    }
}
