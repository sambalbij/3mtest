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

    public void storeEvent(Event event) {
        eventRepository.storeEvent(event);
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
}
