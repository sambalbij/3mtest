package org.driem.api.dummy;

import org.driem.api.Event;
import org.driem.api.EventRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class DummyEventRepository implements EventRepository {
    @Override
    public List<Event> loadAllEvents() {
        return Arrays.asList(
                new Event("Vakantie Kreta", false),
                new Event("Elasticon", false));
    }
}
