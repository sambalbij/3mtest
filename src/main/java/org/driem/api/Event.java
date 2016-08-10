package org.driem.api;

import java.rmi.activation.ActivationID;

import java.util.*;

public class Event {
    int ID;
    private String name;
    private Map<String, Participant> participants = new HashMap<>();

    private List<Activity> activities;
    private Boolean finished = false;
    private String description;



    public Event(String name, String description, Boolean finished) {
        this.name = name;
        this.finished = finished;
        this.description = description;
    }

    public boolean addParticipant(String name, int id)
    {
        participants.put(name, new Participant(name, id));
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<String, Participant> participants) {
        this.participants = participants;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Event() {
    }
}
