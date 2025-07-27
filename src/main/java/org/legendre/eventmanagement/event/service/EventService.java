package org.legendre.eventmanagement.event.service;

import org.legendre.eventmanagement.event.model.Event;
import org.legendre.eventmanagement.event.model.EventRequest;

import java.util.List;
import java.util.Optional;

public interface EventService {
    Event createEvent(EventRequest request);

    Optional<Event> getEventByName(String name);

    List<Event> getAll();

    Event updateEvent(EventRequest request, String name);

    void deleteEvent(String name);
}
