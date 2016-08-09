package org.driem.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Event> events() {
        return eventService.findAllEvents();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void storeEvent(@RequestBody Event event) {
        eventService.storeEvent(event);
    }

}
