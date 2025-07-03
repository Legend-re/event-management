package org.legendre.eventmanagement.event.controller;

import org.legendre.eventmanagement.event.Event;
import org.legendre.eventmanagement.event.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.api.APIs.*;

@RestController
@RequestMapping(EVENT_URL)
public class EventRestController {

    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(CREATE_PATH)
    private Event createEvent(@RequestBody Event request) {
        return eventService.createEvent(request);
    }

    @GetMapping(GET_PATH)
    private Optional<Event> getEvent(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return eventService.getEventByName(name);
    }

    @GetMapping
    private List<Event> getEvent() {
        return eventService.getAll();
    }

    @PutMapping(UPDATE_PATH)
    private Event updateEvent(@RequestBody Event request, @RequestParam String name) {
        return eventService.updateEvent(request, name);
    }

    @DeleteMapping(DELETE_PATH)
    private Optional<Event> deleteEvent(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return eventService.deleteEvent(name);

    }

}