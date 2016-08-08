package org.driem.api;

public class Event {
    private String name;
    private Boolean finished;

    public Event() {
    }

    public Event(String name, Boolean finished) {
        this.name = name;
        this.finished = finished;
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
}
