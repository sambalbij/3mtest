package org.driem.api;

/**
 * Overview object of Event used in list screens
 */
public class EventOverview {
    private int ID;
    private String name;
    private String description;
    private Boolean finished;

    public EventOverview() {
    }

    public EventOverview(int ID, String name, String description, Boolean finished) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.finished = finished;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
