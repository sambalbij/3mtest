package org.driem.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/event")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(method = GET)
    public List<EventOverview> events() {
        return eventService.findAllEvents();
    }

    @RequestMapping(method = POST)
    public void storeEvent(@RequestBody Event event) {
        eventService.storeEvent(event);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Event obtainEvent(@PathVariable int id) {
        logger.debug("Load the event with id {}", id);
        return eventService.obtainEvent(id);
    }

    @RequestMapping(value = "/{id}/participant", method = POST)
    public void addParticipantToEvent(@PathVariable int id,
                                      @RequestBody Participant participant) {
        logger.debug("Add participant to event with id {}", id);
        eventService.addParticipantToEvent(id, participant);
    }

    @RequestMapping(value = "/{id}/activity", method = POST)
    public void addActivityToEvent(@PathVariable int id,
                                   @RequestBody Activity activity) {
        logger.debug("Add activity to event with id {}", id);
        eventService.addActivityToEvent(id, activity);
    }
}
