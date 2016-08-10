package org.driem.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    private EventRepository eventRepository;
  //  private ParticipantRepository participantRepository;

    @Autowired
    public EventService(EventRepository eventRepository){//, ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
      //  this.participantRepository = participantRepository;
    }

    public Map<Integer,Event> findAllEvents() {
        Map<Integer,Event> events = eventRepository.loadAllEvents();
        logger.debug("I have found {} events", events.size());
        return events;
    }

    public void storeEvent(Event event) {
        eventRepository.storeEvent(event);
    }
}
