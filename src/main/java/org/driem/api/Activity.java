package org.driem.api;


import java.util.List;

public class Activity {
    private String name, desciption;
    private List<String> participants;
    private double cost;
    private List<Item> items;

    public Activity(String name, String desciption, List<String> participants, List<Item> items) {
        this.name = name;
        this.desciption = desciption;
        this.participants = participants;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
