package org.legendre.eventmanagement.event.controller;

import org.legendre.eventmanagement.event.model.Event;
import org.legendre.eventmanagement.event.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ResponseEntity<Event> createEvent(@RequestBody Event request) {
        return new ResponseEntity<>(eventService.createEvent(request), HttpStatus.CREATED);
    }

    @GetMapping(GET_PATH)
    private ResponseEntity<Optional<Event>> getEvent(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return new ResponseEntity<>(eventService.getEventByName(name), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Event>> getEvent() {
        return new ResponseEntity<>(eventService.getAll(), HttpStatus.OK);
    }

    @PutMapping(UPDATE_PATH)
    private ResponseEntity<Event> updateEvent(@RequestBody Event request, @RequestParam String name) {
        return new ResponseEntity<>(eventService.updateEvent(request, name), HttpStatus.OK);
    }

    @DeleteMapping(DELETE_PATH)
    private ResponseEntity<Void> deleteEvent(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        eventService.deleteEvent(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}