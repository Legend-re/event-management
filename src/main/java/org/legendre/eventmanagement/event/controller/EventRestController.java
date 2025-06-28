package org.legendre.eventmanagement.event.controller;

import org.legendre.eventmanagement.event.Event;
import org.legendre.eventmanagement.event.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.legendre.eventmanagement.api.APIs.*;

@RestController
@RequestMapping(EVENT_URL)
public class EventRestController {

    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

//CRUD
//    @PostMapping
//    @GetMapping
//    @PatchMapping
//    @PutMapping
//    @DeleteMapping

    @PostMapping(CREATE_EVENT_PATH)
    private Event createEvent(@RequestBody Event request) {
        return eventService.createEvent(request);
    }
}