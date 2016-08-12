package org.driem.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
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
    public void storeEvent(@RequestBody EventOverview eventOverview) {
        eventService.storeEvent(eventOverview);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Event obtainEvent(@PathVariable int id) {
        logger.debug("Load the event with id {}", id);
        return eventService.obtainEvent(id);
    }

    @RequestMapping(value = "/{id}/participant", method = POST)
    public void addParticipantToEvent(@PathVariable int id, @RequestBody Participant participant) {
        logger.debug("Add participant to event with id {}", id);
        eventService.addParticipantToEvent(id, participant);
    }

    @RequestMapping(value = "/{eventId}/participant/{participantId}", method = DELETE)
    public void deleteParticipantFromEvent(@PathVariable int eventId, @PathVariable int participantId) {
        logger.debug("Remove participant with id {} from event with id {}", participantId, eventId);
        eventService.removeParticipantFromEvent(eventId, participantId);
    }

    @RequestMapping(value = "/{id}/activity", method = POST)
    public void addActivityToEvent(@PathVariable int id, @RequestBody Activity activity) {
        logger.debug("Add activity to event with id {}", id);
        eventService.addActivityToEvent(id, activity);
    }

    @RequestMapping(value = "/{eventId}/activity/{activityId}", method = DELETE)
    public void removeActivityFromEvent(@PathVariable int eventId, @PathVariable int activityId) {
        logger.debug("Remove activity with id {} from event with id {}", activityId, eventId);
        eventService.removeActivityFromEvent(eventId, activityId);
    }

    @RequestMapping(value = "/{eventId}/activity/{activityId}/participant/{participantId}", method = POST)
    public void addParticipantToActivity(@PathVariable int eventId, @PathVariable int activityId, @PathVariable int participantId) {
        eventService.addParticipantToActivity(eventId, activityId, participantId);
    }

    @RequestMapping(value = "/{eventId}/activity/{activityId}/participant/{participantId}", method = DELETE)
    public void removeParticipantFromActivity(@PathVariable int eventId, @PathVariable int activityId, @PathVariable int participantId) {
        eventService.removeParticipantFromActivity(eventId, activityId, participantId);
    }

    @RequestMapping(value = "/{eventId}/activity/{activityId}/item", method = POST)
    public void addItemToActivity(@PathVariable int eventId, @PathVariable int activityId, @RequestBody Item item) {
        eventService.addItemToActivity(eventId, activityId, item);
    }

    @RequestMapping(value = "/{eventId}/activity/{activityId}/item/{itemId}/participant/{participantId}", method = POST)
    public void addParticipantToItem(@PathVariable int eventId,
                                         @PathVariable int activityId,
                                         @PathVariable int itemId,
                                         @PathVariable int participantId) {
        eventService.addParticipantToItem(eventId, activityId, itemId, participantId);
    }

    @RequestMapping(value = "/{eventId}/activity/{activityId}/item/{itemId}", method = DELETE)
    public void removeItemFromActivity(@PathVariable int eventId, @PathVariable int activityId, @PathVariable int itemId) {
        eventService.removeItemFromActivity(eventId, activityId, itemId);
    }

    @RequestMapping(value = "/{eventId}/activity/{activityId}/item/{itemId}/participant/{participantId}", method = DELETE)
    public void removeParticipantFromItem(@PathVariable int eventId,
                                         @PathVariable int activityId,
                                         @PathVariable int itemId,
                                         @PathVariable int participantId) {
        eventService.removeParticipantFromItem(eventId, activityId, itemId, participantId);
    }

}
