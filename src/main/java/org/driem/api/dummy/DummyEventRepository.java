package org.driem.api.dummy;

import org.driem.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class DummyEventRepository implements EventRepository {
    private static final Logger logger = LoggerFactory.getLogger(DummyEventRepository.class);

    private Map<Integer, Event> events = new HashMap<>();

    @PostConstruct
    public void init() {
        logger.debug("In PostConstruct creating two events");
        events.put(0, new Event("Vakantie Kreta", "Met mijn vrienden naar Kreta", false));
        events.put(1, new Event("Elasticon", "Business trip naar San Francisco", false));
    }

    @Override
    public List<Event> loadAllEvents() {
        return Collections.unmodifiableList(new ArrayList<Event>(events.values()));
    }

    @Override
    public Event storeEvent(Event event) {
        events.put(event.getID(),event);
        logger.debug("Stored an event with name {}", event.getName());
        return event;
    }

    @Override
    public void addParticipantToEvent(int eventID, String name) {
        events.get(eventID).addParticipant(name);
        logger.debug("Added particpant with name {}", name);
    }

    @Override
    public void removeParticipantFromEvent(int eventID, String name) {
        events.get(eventID).removeParticipant(name);
        logger.debug("Removed particpant with name {}", name);
    }

    @Override
    public void addActivityToEvent(int eventID, Activity a) {
        events.get(eventID).addActivity(a);
        logger.debug("Added activity with name {} and ID {}", a.getName(),a.getID());
    }

    @Override
    public void removeActivity(int eventID, int activityID) {
        events.get(eventID).removeActivity(activityID);
        logger.debug("Removed activity with ID {}", activityID);
    }

    @Override
    public void addParticipantToActivity(int eventID, int activityID, String name) {
        events.get(eventID).addParticipantToActivity(activityID,name);
        logger.debug("Added participant with name {} to activity ID {}", name,activityID);
    }

    @Override
    public void removeParticipantFromActivity(int eventID, int activityID, String name) {
        events.get(eventID).removeParticipantFromActivity(activityID,name);
        logger.debug("Removed participant with name {} from activity with ID {}", name, activityID);
    }

    @Override
    public void addItemToActivity(int eventID, int activityID, Item i) {
        events.get(eventID).addItemToActivity(activityID,i);
        logger.debug("Added item with name {} to activity ID {}", i.getName(),activityID);
    }

    @Override
    public void removeItemFromActivity(int eventID, int activityID, int itemID) {
        events.get(eventID).removeItemFromActivity(activityID,itemID);
        logger.debug("Removed item with ID {} from activity with ID {}", itemID, activityID);
    }

    @Override
    public void addParticipantToItem(int eventID, int activityID, int itemID, String name) {
        events.get(eventID).addParticipantToItem(activityID, itemID, name);
        logger.debug("Added participant with name {} to item with ID {}",name, itemID);
    }

    @Override
    public void removeParticipantFromItem(int eventID, int activityID, int itemID, String name) {
        events.get(eventID).removeParticipantFromItem(activityID,itemID,name);
    }
}
