package org.driem.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public void storeEvent(EventOverview eventOverview) {

        eventRepository.storeEvent(new Event(eventOverview.getID(),eventOverview.getName(),eventOverview.getDescription(),eventOverview.getFinished()));
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
        eventRepository.addParticipantToItem(eventId,activityId, itemId, participantId);
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
}
