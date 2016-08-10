package org.driem.api;


import java.util.List;

public class Item {
    private String name, description;
    private List<String> participants;
    private double cost;
    private int ID;

    public Item(String name, String description, int ID, List<String> participants) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.participants = participants;
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

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void addParticipant(String name) {
        participants.add(name);
    }

    public void removeParticipant(String name){
        participants.remove(name);
    }
}
