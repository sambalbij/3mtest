package org.driem.api.dummy;

import org.driem.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("dummy")
public class DummyEventRepository implements EventRepository {
    private static final Logger logger = LoggerFactory.getLogger(DummyEventRepository.class);

    private Map<Integer, Event> events = new HashMap<>();

    @PostConstruct
    public void init() {
        logger.debug("In PostConstruct creating two events");
        createEvent(0, "Vakantie Kreta", "Met mijn vrienden naar Kreta");
        createEvent(1, "Elasticon", "Business trip naar San Francisco");
    }

    @Override
    public List<Event> loadAllEvents() {
        return Collections.unmodifiableList(new ArrayList<>(events.values()));
    }

    @Override
    public int storeEvent(Event event) {
        events.put(event.getID(),event);
        logger.debug("Stored an event with name {}", event.getName());
        return event.getID();
    }

    @Override
    public void addParticipantToEvent(int eventID, Participant participant) {
        events.get(eventID).addParticipant(participant);
        logger.debug("Added particpant with name {}", participant.getName());
    }

    @Override
    public void removeParticipantFromEvent(int eventID, int participantID) {
        events.get(eventID).removeParticipant(participantID);
        logger.debug("Removed particpant with ID {}", participantID);
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
    public void setActivityCost(int eventId,int activityId, double cost)
    {
        events.get(eventId).setActivityCost(activityId,cost);
        logger.debug("Set cost for activity with ID {} to {}", activityId, cost);
    }

    @Override
    public void addParticipantToActivity(int eventID, int activityID, int participantID) {
        events.get(eventID).addParticipantToActivity(activityID, participantID);
        logger.debug("Added participant with ID {} to activity ID {}", participantID,activityID);
    }

    @Override
    public void removeParticipantFromActivity(int eventID, int activityID, int participantID) {
        events.get(eventID).removeParticipantFromActivity(activityID, participantID);
        logger.debug("Removed participant with ID {} from activity with ID {}", participantID, activityID);
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
    public void addParticipantToItem(int eventID, int activityID, int itemID, int participantID) {
        events.get(eventID).addParticipantToItem(activityID, itemID, participantID);
        logger.debug("Added participant with ID {} to item with ID {}",participantID, itemID);
    }

    @Override
    public void removeParticipantFromItem(int eventID, int activityID, int itemID, int participantID) {
        events.get(eventID).removeParticipantFromItem(activityID,itemID,participantID);
        logger.debug("Removed participant with ID {} from item with ID {}", participantID, itemID);
    }

    @Override
    public Event loadEvent(int id) {
        return events.get(id);
    }

    private void createEvent(int id, String name, String description) {
        Event event1 = new Event(id, name, description, false);
        this.storeEvent(event1);
    }

    @Override
    public void setPayerToActivity(int eventID, int activityID, int payerID) {
       events.get(eventID).getActivities().get(activityID).setPayer(payerID);
    }
}
