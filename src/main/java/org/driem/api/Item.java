package org.driem.api;


import java.util.List;

public class Item {
    private int ID;
    private double cost;
    private String name;
    private String description;
    private List<Integer> participants;

    public Item() {
    }

    public Item(int ID, double cost, String name, String description) {
        this(ID, cost, name, description, null);
    }

    public Item(int ID, double cost, String name, String description, List<Integer> participants) {
        this.ID = ID;
        this.cost = cost;
        this.name = name;
        this.description = description;
        if (participants != null) {
            this.participants = participants;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void addParticipant(int ID) {
        participants.add(ID);
    }

    public void removeParticipant(int ID) {
        participants.remove(ID);
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
