package org.driem.api.dummy;

import org.driem.api.Event;
import org.driem.api.EventRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class DummyEventRepository implements EventRepository {

    private List<Event> events = new ArrayList<>();

    @Override
    public List<Event> loadAllEvents() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public Event storeEvent(Event event) {
        events.add(event);
        return event;
    }

    @PostConstruct
    public void init() {
        events.addAll(Arrays.asList(
                new Event("Vakantie Kreta", "Met mijn vrienden naar Kreta", false),
                new Event("Elasticon", "Business trip naar San Francisco", false)));
    }
}
