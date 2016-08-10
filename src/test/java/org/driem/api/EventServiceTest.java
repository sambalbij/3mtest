package org.driem.api;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EventServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    EventRepository eventRepository;

    @InjectMocks
    private EventService service;

    @Test
    public void findAllEvents() throws Exception {
        when(eventRepository.loadAllEvents()).thenReturn(allEvents());

        List<Event> allEvents = service.findAllEvents();

        assertEquals("Total number of events not correct", 2, allEvents.size());

        verify(eventRepository, times(1)).loadAllEvents();
    }

    @Test
    public void storeEvent() throws Exception {
        Event event = new Event("test", "test description", false);
        when(eventRepository.storeEvent(event)).thenReturn(event);

        service.storeEvent(event);

        verify(eventRepository, times(1)).storeEvent(eq(event));
    }


    private List<Event> allEvents() {
        List<Event> all = new ArrayList<>();
        all.addAll(Arrays.asList(
                new Event("Vakantie Kreta", "Met mijn vrienden naar Kreta", false),
                new Event("Elasticon", "Business trip naar San Francisco", false)));
        return all;
    }
}