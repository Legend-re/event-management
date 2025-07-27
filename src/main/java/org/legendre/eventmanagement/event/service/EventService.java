package org.legendre.eventmanagement.event.service;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.event.model.Event;
import org.legendre.eventmanagement.event.model.EventRequest;
import org.legendre.eventmanagement.event.model.repository.EventRepository;
import org.legendre.eventmanagement.host.service.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final HostService hostService;

    public Event createEvent(EventRequest request) {
        var findHost = hostService.getHostByName(request.getHostName())
                .orElseThrow(
                        () -> new IllegalArgumentException("Host does not exist")
                );

        return eventRepository.save(
                Event.builder()
                        .name(request.getName())
                        .location(request.getLocation())
                        .host(findHost.getName())
                        .date(request.getDate()).build());
    }

    public Optional<Event> getEventByName(String name) {
        return Optional.ofNullable(eventRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Event not found")));
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event updateEvent(EventRequest request, String name) {
        var findEvent = eventRepository.findByName(name).orElseThrow(
                () -> new IllegalArgumentException("Queried event does not exist")
        );

        eventRepository.findByName(request.getName())
                .ifPresent(event -> {
                    throw new IllegalArgumentException("Event already exists");
                });

        return eventRepository.save(
                findEvent.toBuilder()
                .name(request.getName())
                .location(request.getLocation())
                .date(request.getDate()).build());
    }

    public void deleteEvent(String name) {
        eventRepository.findByName(name).ifPresent(eventRepository::delete);
    }
}