package org.driem.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventOverview> findAllEvents() {
        List<Event> events = eventRepository.loadAllEvents();
        if (events == null) {
            events = new ArrayList<>();
        }
        logger.debug("I have found {} events", events.size());

        return events
                .stream()
                .map(event -> new EventOverview(
                        event.getID(),
                        event.getName(),
                        event.getDescription(),
                        event.getFinished())
                )
                .collect(Collectors.toList());
    }

    public int storeEvent(EventOverview eventOverview) {

        return eventRepository.storeEvent(new Event(eventOverview.getID(),eventOverview.getName(),eventOverview.getDescription(),eventOverview.getFinished()));
    }

    public Event obtainEvent(int id) {
        return eventRepository.loadEvent(id);
    }

    public void addParticipantToEvent(int id, Participant participant) {
        eventRepository.addParticipantToEvent(id, participant);
    }

    public void removeParticipantFromEvent(int eventId, int participantId) {
        eventRepository.removeParticipantFromEvent(eventId, participantId);
    }

    public void addActivityToEvent(int id, Activity activity) {
        eventRepository.addActivityToEvent(id, activity);
    }

    public void removeActivityFromEvent(int eventId, int activityId) {
        eventRepository.removeActivity(eventId, activityId);
    }

    public void addParticipantToActivity(int eventId, int activityId, int participantId) {
        eventRepository.addParticipantToActivity(eventId, activityId, participantId);
    }

    public void addItemToActivity(int eventId, int activityId, Item item) {
        eventRepository.addItemToActivity(eventId, activityId, item);
    }

    public void addParticipantToItem(int eventId, int activityId, int itemId, int participantId) {

        Event event = eventRepository.loadEvent(eventId);
        if (event.getParticipants() != null && !event.getParticipants().containsKey(participantId)){
            throw new IllegalArgumentException("Participant does not exist in this event");
        }
        else if (participantNotInActivity(event, activityId, participantId)) {
            eventRepository.addParticipantToActivity(eventId,activityId,participantId);
        }
        eventRepository.addParticipantToItem(eventId,activityId, itemId, participantId);
    }

    private boolean participantNotInActivity(Event event, int activityId, int participantId){
        Activity activity = event.getActivities().get(activityId);
        if (activity == null){
            throw new IllegalArgumentException("Activity with id "+activityId+" does not exist");
        }
        return !activity.getParticipants().contains(participantId);
    }

    public void removeItemFromActivity(int eventId, int activityId, int itemId) {
        eventRepository.removeItemFromActivity(eventId, activityId, itemId);
    }

    public void removeParticipantFromItem(int eventId, int activityId, int itemId, int participantId) {
        eventRepository.removeParticipantFromItem(eventId, activityId, itemId, participantId);
    }

    public void removeParticipantFromActivity(int eventId, int activityId, int participantId) {
        eventRepository.removeParticipantFromActivity(eventId, activityId, participantId);
    }

    public EndBill computeEndBill(int eventID) {
        Event event = obtainEvent(eventID);
        EndBill bill  = new EndBill();
        event.getParticipants().values().forEach(participants -> bill.addEmptyParticipantToPay(participants));

        for (Activity activity: event.getActivities().values()) {
            double totalCostActivity = activity.getCost();
            int activityParticipants = activity.getParticipants().size();
            if (activityParticipants==0) {
                continue;
            }
            for (Item item: activity.getItems().values()) {
                // item betalen per participant
                double costItem = item.getCost();
                int itemparticipants = item.getParticipants().size();
                if (itemparticipants == 0) {
                    continue;
                }
                for (int participantID: item.getParticipants()) {
                    bill.addAmountToParticipantToPay(participantID, + (costItem / itemparticipants),"Item: " + item.getName());
                }
                totalCostActivity-=costItem;
            }
            // rest van de activity betalen
            for (int id :activity.getParticipants())
                bill.addAmountToParticipantToPay(id,+(totalCostActivity/activityParticipants),"Activity: " + activity.getName());

            if (activity.getPayer()!=-1)
            {
                bill.addPaymentToPayer(activity.getPayer(),activity.getCost());
            }
        }

        return bill;
    }
}
