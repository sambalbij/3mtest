package org.driem.api;

/**
 * Created by martijn.koenis on 8-8-2016.
 */
public class Participant {
    private String name;
    private int id;

    public Participant(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
