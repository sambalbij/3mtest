package org.driem.api;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity {
    private String name;
    private String description;
    private List<Integer> participants;
    private double cost;
    private Map<Integer, Item> items = new HashMap<>();
    private int ID;

    public Activity() {
    }

    public Activity(String name, String description, int ID, List<Integer> participants, Map<Integer, Item> items) {
        this.name = name;
        this.description = description;
        this.participants = participants;
        this.items = items;
        this.ID = ID;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void addParticipant(int ID){
        participants.add(ID);
    }

    public void removeParticipant(int ID){
        participants.remove(ID);
    }

    public void addItem(Item item){
        items.put(item.getID(), item);
    }

    public void removeItem(int ID){
        items.remove(ID);
    }

    public void addParticipantToItem(int ID, int participantID){
        items.get(ID).addParticipant(participantID);
    }

    public void removeParticipantFromItem(int ID, int participantID){
        items.get(ID).removeParticipant(participantID);
    }
}
