package org.driem.api;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity {
    private String name;
    private String description;
    private List<String> participants;
    private double cost;
    private Map<Integer, Item> items = new HashMap<>();
    private int ID;

    public Activity() {
    }

    public Activity(String name, String desciption, int ID, List<String> participants, Map<Integer, Item> items) {
        this.name = name;
        this.description = desciption;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void addParticipant(String name){
        participants.add(name);
    }

    public void removeParticipant(String name){
        participants.remove(name);
    }

    public void addItem(Item item){
        items.put(item.getID(), item);
    }

    public void removeItem(int ID){
        items.remove(ID);
    }

    public void addParticipantToItem(int ID, String name){
        items.get(ID).addParticipant(name);
    }

    public void removeParticipantFromItem(int ID, String name){
        items.get(ID).removeParticipant(name);
    }
}
