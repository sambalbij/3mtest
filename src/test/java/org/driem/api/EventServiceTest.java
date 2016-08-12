package org.driem.api;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
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

    @BeforeClass
    public static void eenkeer() {
        System.out.println("Maar een keer");
    }

    @AfterClass
    public static void eenkeeropruimen() {
        System.out.println("Maar een keer opruimen");
    }

    @Before
    public void init() {
        System.out.println("Een test starten");
    }

    @After
    public void after() {
        System.out.println("Een test eindigen");
    }

    @Test
    public void findAllEvents() throws Exception {
        when(eventRepository.loadAllEvents()).thenReturn(allEvents());

        List<EventOverview> allEvents = service.findAllEvents();

        assertEquals("Total number of events not correct", 2, allEvents.size());

        verify(eventRepository, times(1)).loadAllEvents();
    }

    @Test
    public void findAllEvents_noResultsFromRepo() {
        when(eventRepository.loadAllEvents()).thenReturn(null);

        List<EventOverview> allEvents = service.findAllEvents();

        assertNotNull("Events list should not be null", allEvents);
        assertEquals("Total number of events should be 0", 0, allEvents.size());

        verify(eventRepository, times(1)).loadAllEvents();
    }

    @Test
    public void findAllEvents_nonUniqueParticipant() {
        Event event = new Event(0, "test", "test description", false);
        EventOverview eventOverview = new EventOverview(0, "test", "test description", false);
        Mockito.doThrow(new NonUniqueParticipantNameException("test")).when(eventRepository).storeEvent(any(Event.class));

        try {
            service.storeEvent(eventOverview);
            fail("A NonUniqueParticipantNameException should have been thrown");
        } catch (NonUniqueParticipantNameException e) {
            // as expected
        }

        verify(eventRepository, times(1)).storeEvent(any(Event.class));
    }



    @Test
    public void storeEvent() throws Exception {
        Event event = new Event(0, "test", "test description", false);
        EventOverview eventOverview = new EventOverview(0, "test", "test description", false);

        service.storeEvent(eventOverview);

        verify(eventRepository, times(1)).storeEvent(any(Event.class));
    }

    @Test
    public void computeEndBillSimple() {

        when(eventRepository.loadEvent(1)).thenReturn(createEvent(1));
        EndBill endBill = service.computeEndBill(1);

        verify(eventRepository, times(1)).loadEvent(1);
        assertEquals(2,endBill.getPartipantToPays().size());
        assertEquals(7.5,endBill.getPartipantToPays().get(1).getToPay(),0.1);
        assertEquals(2.5,endBill.getPartipantToPays().get(2).getToPay(),0.1);
    }

    private Event createEvent(int id) {
        Event event = new Event(id, "testevent", "dit is een test event", false);
        event.addActivity(new Activity(1, "testactivity","dit is een test event",10.00));
        event.addItemToActivity(1,new Item(1,5.0,"testitem","dit is een test item"));
        event.addParticipant(new Participant("testParticipant1",1));
        event.addParticipantToActivity(1,1);
        event.addParticipantToItem(1,1,1);

        event.addParticipant(new Participant("testParticipant2",2));
        event.addParticipantToActivity(1,2);

        return event;
    }

    private List<Event> allEvents() {
        List<Event> all = new ArrayList<>();
        all.addAll(Arrays.asList(
                new Event(0, "Vakantie Kreta", "Met mijn vrienden naar Kreta", false),
                new Event(1, "Elasticon", "Business trip naar San Francisco", false)));
        return all;
    }



}