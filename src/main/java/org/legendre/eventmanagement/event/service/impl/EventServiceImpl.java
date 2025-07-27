package org.legendre.eventmanagement.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.event.model.Event;
import org.legendre.eventmanagement.event.model.EventRequest;
import org.legendre.eventmanagement.event.model.repository.EventRepository;
import org.legendre.eventmanagement.event.service.EventService;
import org.legendre.eventmanagement.exception.DuplicateRecordException;
import org.legendre.eventmanagement.exception.ErrorMessages;
import org.legendre.eventmanagement.exception.ErrorResponse;
import org.legendre.eventmanagement.exception.RecordNotFoundException;
import org.legendre.eventmanagement.host.service.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                                new ErrorResponse(ErrorMessages.HOST_NOT_FOUND)
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
                        new ErrorResponse(ErrorMessages.EVENT_NOT_FOUND)
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
                        new ErrorResponse(ErrorMessages.EVENT_NOT_FOUND)
                )
        );

        eventRepository.findByName(request.getName())
                .ifPresent(event -> {
                    throw new DuplicateRecordException(
                            new ErrorResponse(ErrorMessages.EVENT_ALREADY_EXIST)
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