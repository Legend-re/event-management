package org.legendre.eventmanagement.event.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legendre.eventmanagement.event.model.Event;
import org.legendre.eventmanagement.event.model.EventRequest;
import org.legendre.eventmanagement.event.model.repository.EventRepository;
import org.legendre.eventmanagement.event.service.EventService;
import org.legendre.eventmanagement.exception.DuplicateRecordException;
import org.legendre.eventmanagement.exception.ErrorCode;
import org.legendre.eventmanagement.exception.ErrorResponse;
import org.legendre.eventmanagement.exception.RecordNotFoundException;
import org.legendre.eventmanagement.host.service.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.exception.ErrorMessages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final HostService hostService;

    @Override
    public Event createEvent(EventRequest request) {
        log.info("creating an event for host: {}", request.getHostName());
        var findHost = hostService.getHostByName(request.getHostName())
                .orElseThrow(() -> {
                    log.error("Host not found: {}", request.getHostName());
                    return new RecordNotFoundException(
                            new ErrorResponse(HOST_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                    );
                });

        var savedEvent = eventRepository.save(Event.builder()
                .name(request.getName())
                .location(request.getLocation())
                .host(findHost.getName())
                .date(request.getDate())
                .build());

        log.info("Event created successfully: {}", savedEvent.getName());
        return savedEvent;
    }

    @Override
    public Optional<Event> getEventByName(String name) {
        log.info("Fetching events by name: {}", name);
        return Optional.ofNullable(eventRepository.findByName(name)
                .orElseThrow(() -> {log.error("Event not found: {}", name);
                    return new RecordNotFoundException(
                        new ErrorResponse(EVENT_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                );})
        );
    }

    @Override
    public List<Event> getAll() {
        log.info("Fetching all events");
        return eventRepository.findAll();
    }

    @Override
    public Event updateEvent(EventRequest request, String name) {
        log.info("Updating an event: {}", name);
        var findEvent = eventRepository.findByName(name).orElseThrow(
                () -> {log.error("Event not found: {}", name);
                       return new RecordNotFoundException(
                        new ErrorResponse(EVENT_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                );}
        );

        eventRepository.findByName(request.getName())
                .ifPresent(event -> {
                    log.error("Event already exist: {}", request.getName());
                    throw new DuplicateRecordException(
                            new ErrorResponse(EVENT_ALREADY_EXIST.getMessage(), ErrorCode.RSC02)
                    );
                });

        Event updatedEvent = eventRepository.save(
                findEvent.toBuilder()
                        .name(request.getName())
                        .location(request.getLocation())
                        .date(request.getDate())
                        .build()
        );

        log.info("Event updated successfully: {}", updatedEvent.getName());
        return updatedEvent;
    }

    @Override
    public void deleteEvent(String name) {
        log.info("Deleting an event: {}", name);
        eventRepository.findByName(name).ifPresent(eventRepository::delete);
        log.info("Event deleted successfully: {}", name);
    }
}