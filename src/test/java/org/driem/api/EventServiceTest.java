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
import java.util.Map;

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

    private List<Event> allEvents() {
        List<Event> all = new ArrayList<>();
        all.addAll(Arrays.asList(
                new Event(0, "Vakantie Kreta", "Met mijn vrienden naar Kreta", false),
                new Event(1, "Elasticon", "Business trip naar San Francisco", false)));
        return all;
    }

    @Test
    public void testBill() throws Exception {
        // create an event, with activities, with costs, with subitems, and participants
        Event event = new Event(0,"test","test description",false);
        Participant participant1 = new Participant("P1",1);
        Participant participant2 = new Participant("P2",2);
        Participant participant3 = new Participant("P3",3);

        Activity activity1 = new Activity("Activity 1","no items",1,50);
        Activity activity2 = new Activity("Activity 2","with items",2,13.50);

        Item item1 = new Item(1,2.5,"Item 1","tweevijftig");
        Item item2 = new Item(1,5,"Item","vijf");

        event.addParticipant(participant1);
        event.addParticipant(participant2);
        event.addParticipant(participant3);

        event.addActivity(activity1);
        event.addActivity(activity2);

        event.addItemToActivity(2,item1);
        event.addItemToActivity(2,item2);

        event.addParticipantToActivity(1,1);
        event.addParticipantToActivity(1,2);
        event.addParticipantToActivity(2,1);
        event.addParticipantToActivity(2,2);
        event.addParticipantToActivity(2,3);

        event.addParticipantToItem(2,1,1);
        event.addParticipantToItem(2,1,2);
        event.addParticipantToItem(2,2,1);

        // total cost activity 1 = 50, no items, 2 participants
        // P1: 25;
        // P2: 25

        // total cost activity 2 = 13.50, 3 participants
        // Item 1: 2.50, shared by P1, P2
        // Item 2: 5, for P1
        // remainder 13.50-(2.5-5) = 6 / 3 = 2 each
        // Item 1 = 2.50/2 = 1.25 for P1 and P2 each
        // Item 2 = 5 for P1
        // P1: 2 + 1.25 + 5 = 8.25
        // P2: 2 + 1.25 = 3.25
        // P3: 2

        // total for entire event
        // P1: 33.25
        // P2: 28.25
        // P3: 2
        // Sum: 63.50

        Map<Integer,Double> billMap = service.makeBill(0);
        assertEquals("P1 has 33.25",33.25,billMap.get(1),0.01);


    }
}