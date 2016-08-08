package org.driem.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//        EventService eventService = context.getBean(EventService.class);
//
//        List<Event> allEvents = eventService.findAllEvents();
//
//        allEvents.forEach(event -> System.out.println(event.getName()));

        System.out.println("Done");
    }
}
