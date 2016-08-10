package org.driem.api;


import java.util.List;

public class Item {
    private String name, description;
    private List<String> participants;
    private double cost;

    public Item(String name, String description, List<String> participants) {
        this.name = name;
        this.description = description;
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
}
