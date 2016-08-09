package org.driem.api;

public class Event {
    private String name;
    private Boolean finished = false;
    private String description;

    public Event() {
    }

    public Event(String name, String description, Boolean finished) {
        this.name = name;
        this.finished = finished;
        this.description = description;
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
}
