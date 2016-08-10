package org.driem.api;

/**
 * Created by martijn.koenis on 8-8-2016.
 */
public class Participant {
    private String name;
    private int ID;

    public Participant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
