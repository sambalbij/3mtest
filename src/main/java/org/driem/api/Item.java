package org.driem.api;


import java.util.List;

public class Item {
    private String name, description;
    private List<Integer> participants;
    private double cost;
    private int ID;

    public Item(String name, String description, int ID, List<Integer> participants) {
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

    public void removeParticipant(int ID ){
        participants.remove(ID);
    }
}
