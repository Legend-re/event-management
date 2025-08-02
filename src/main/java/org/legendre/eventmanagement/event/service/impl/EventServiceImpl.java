package org.legendre.eventmanagement.event.service.impl;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final HostService hostService;

    @Override
    public Event createEvent(EventRequest request) {
        var findHost = hostService.getHostByName(request.getHostName())
                .orElseThrow(
                        () -> new RecordNotFoundException(
                                new ErrorResponse(
                                        HOST_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                        )
                );

        return eventRepository.save(
                Event.builder()
                        .name(request.getName())
                        .location(request.getLocation())
                        .host(findHost.getName())
                        .date(request.getDate()).build());
    }

    @Override
    public Optional<Event> getEventByName(String name) {
        return Optional.ofNullable(eventRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(
                        new ErrorResponse(EVENT_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                ))
        );
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event updateEvent(EventRequest request, String name) {
        var findEvent = eventRepository.findByName(name).orElseThrow(
                () -> new RecordNotFoundException(
                        new ErrorResponse(EVENT_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                )
        );

        eventRepository.findByName(request.getName())
                .ifPresent(event -> {
                    throw new DuplicateRecordException(
                            new ErrorResponse(EVENT_ALREADY_EXIST.getMessage(), ErrorCode.RSC02)
                    );
                });

        return eventRepository.save(
                findEvent.toBuilder()
                        .name(request.getName())
                        .location(request.getLocation())
                        .date(request.getDate()).build());
    }

    @Override
    public void deleteEvent(String name) {
        eventRepository.findByName(name).ifPresent(eventRepository::delete);
    }
}