package org.driem.api;

import java.util.List;

public interface EventRepository {
    List<Event> loadAllEvents();

    Event storeEvent(Event event);
}
