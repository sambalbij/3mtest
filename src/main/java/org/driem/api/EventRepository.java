package org.driem.api;

import java.util.List;
import java.util.Map;

public interface EventRepository {
    Map<Integer, Event> loadAllEvents();

    Event storeEvent(Event event);

    void addParticipantToEvent(int eventID, String name);

    void removeParticipantFromEvent(int eventID, String name);

    void addActivityToEvent(int eventID, Activity a);

    void removeActivity(int eventID, int activityID);

    void addParticipantToActivity(int eventID, int activityID, String name);

    void removeParticipantFromActivity(int eventID, int activityID, String name);

    void addItemToActivity(int eventID, int activityID, Item i);

    void removeItemFromActivity(int eventID, int activityID, int itemID);

    void addParticipantToItem(int eventID, int activityID, int itemID, String name);

    void removeParticipantFromItem(int eventID, int activityID, int itemID, String name);


}
