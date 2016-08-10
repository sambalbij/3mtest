package org.driem.api;

import java.util.HashMap;
import java.util.Map;

public class Event {
    private int ID;
    private String name;
    private Map<String, Participant> participants = new HashMap<>();

    private Map<Integer, Activity> activities = new HashMap<>();
    private Boolean finished = false;
    private String description;

    public Event() {
    }

    public Event(int ID, String name, String description, Boolean finished) {
        this.ID = ID;
        this.name = name;
        this.finished = finished;
        this.description = description;
    }

    public void addParticipant(Participant participant) {
        participants.put(participant.getName(), participant);
    }

    public void removeParticipant(String name) {
        participants.remove(name);
    }

    public void addActivity(Activity activity) {
        activities.put(activity.getID(), activity);
    }

    public void removeActivity(int ID) {
        activities.remove(ID);
    }

    public void addParticipantToActivity(int ID, String name) {
        activities.get(ID).addParticipant(name);
    }

    public void removeParticipantFromActivity(int ID, String name) {
        activities.get(ID).removeParticipant(name);
    }

    public void addItemToActivity(int ID, Item item) {
        activities.get(ID).addItem(item);
    }

    public void removeItemFromActivity(int activitiyID, int itemID) {
        activities.get(activitiyID).removeItem(itemID);
    }

    public void addParticipantToItem(int activityID, int itemID, String name) {
        activities.get(activityID).addParticipantToItem(itemID, name);
    }

    public void removeParticipantFromItem(int activityID, int itemID, String name) {
        activities.get(activityID).removeParticipantFromItem(itemID, name);
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

    public Map<Integer, Activity> getActivities() {
        return activities;
    }

    public void setActivities(Map<Integer, Activity> activities) {
        this.activities = activities;
    }
}
