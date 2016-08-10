package org.driem.api.dummy;

import org.driem.api.Event;
import org.driem.api.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class DummyEventRepository implements EventRepository {
    private static final Logger logger = LoggerFactory.getLogger(DummyEventRepository.class);

    private List<Event> events = new ArrayList<>();

    @Override
    public List<Event> loadAllEvents() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public Event storeEvent(Event event) {
        events.add(event);
        logger.debug("Stored an event with name {}", event.getName());
        return event;
    }

    @PostConstruct
    public void init() {
        logger.debug("In PostConstruct creating two events");
        events.addAll(Arrays.asList(
                new Event("Vakantie Kreta", "Met mijn vrienden naar Kreta", false),
                new Event("Elasticon", "Business trip naar San Francisco", false)));
    }
}
