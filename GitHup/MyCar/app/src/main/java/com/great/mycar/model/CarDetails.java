package com.great.mycar.model;

public class CarDetails {
    private String id ,name,speed,size,flax,type,timer,tools,details;

    public CarDetails(String id, String name, String speed, String size, String flax, String type, String timer, String tools, String details) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.size = size;
        this.flax = flax;
        this.type = type;
        this.timer = timer;
        this.tools = tools;
        this.details = details;
    }

    public CarDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFlax() {
        return flax;
    }

    public void setFlax(String flax) {
        this.flax = flax;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
