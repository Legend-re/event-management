package org.legendre.eventmanagement.event.service;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.event.model.Event;
import org.legendre.eventmanagement.event.model.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final List<Event> events = new ArrayList<>();

    public Event createEvent(Event request) {
        return eventRepository.save(
                new Event(request.getId(), request.getName(), request.getLocation(), request.getDate()));
    }

    public Optional<Event> getEventByName(String name) {
        return eventRepository.findByName(name);
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event updateEvent(Event request, String name) {
        var findEvent = eventRepository.findByName(name).orElse(null);

        assert findEvent != null;
        findEvent.setName(request.getName());
        findEvent.setLocation(request.getLocation());
        findEvent.setDate(request.getDate());

        return findEvent;
    }

    public void deleteEvent(String name) {
        eventRepository.findByName(name).ifPresent(eventRepository::delete);
    }
}
