package com.api.actordirectory.ActorDirectory.model;
public class Actor {
    private String name;
    private Integer actorId;
    private double height;
    private String DOB;

    public Actor( String name,Integer actorId, double height, String DOB) {
        this.name = name;
        this.actorId = actorId;
        this.height = height;
        this.DOB = DOB;
    }

    public Actor() {
    }

    public Actor(String name, double height, String DOB) {
        this.name = name;
        this.height = height;
        this.DOB = DOB;
    }

    @Override
    public String toString() {
        return name + "," +
                actorId + ","
                 + height +","
                + DOB ;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}
