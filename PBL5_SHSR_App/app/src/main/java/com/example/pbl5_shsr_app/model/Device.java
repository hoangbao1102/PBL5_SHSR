package com.example.pbl5_shsr_app.model;

public class Device {
    private String name;
    private int status;
    private int speed;

    public Device() {}

    public Device(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public Device(String name, int status, int speed) {
        this.name = name;
        this.status = status;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public int getSpeed() {
        return speed;
    }
}
