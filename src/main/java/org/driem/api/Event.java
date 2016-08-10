package org.driem.api;

import java.util.List;

public class Event {
    int ID;
    private String name;
    private List<Participant> participants;
    private Boolean finished = false;
    private String description;



    public Event(String name, String description, Boolean finished) {
        this.name = name;
        this.finished = finished;
        this.description = description;
    }

    public boolean addParticipant(String name)
    {
        participants.add(new Participant(name));
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

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public Event() {
    }
}
