package org.legendre.eventmanagement.event;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final List<Event> events = new ArrayList<>();

    public Event createEvent(Event request) {
        events.add(request);
        return new Event(request.getName(), request.getLocation(), request.getDate());
    }

    @PostConstruct
    public List<Event> populateEvents() {
        events.add(new Event(
                "Google Conference", "Ajah", LocalDateTime.now()));

        events.add(new Event(
                "Amazon Conference", "Lekki", LocalDateTime.now()));

        events.add(new Event(
                "Microsoft Conference", "Ikeja", LocalDateTime.now()));

        events.add(new Event(
                "Netflix Conference", "VI", LocalDateTime.now()));

        events.add(new Event(
                "Meta Conference", "Ikoyi", LocalDateTime.now()));

        return events;
    }

    public Optional<Event> getEventByName(String name) {
        var foundEvent = events.stream()
                .filter(event -> event.getName().equalsIgnoreCase(name))
                .findFirst();
        foundEvent.ifPresentOrElse((
                event -> System.out.println("Found event: " + name)),
                () -> System.err.println("No event found with name: " + name));

        return foundEvent;
/*
        if (foundEvent.isPresent()) {
            System.out.println("Found event: " + foundEvent.get().getName());
            return foundEvent;
        }
        System.err.println("No event found with name: " + name);
        return Optional.empty();
*/

/*        Optional<Event> foundEvent;
        for (Event event : events) {
          var eventPresent = event.getName().equalsIgnoreCase(name);
          if (!eventPresent) {
              foundEvent = Optional.of(event);
              System.out.println("Found event: " + foundEvent.get().getName());
              return foundEvent;
          }
        }
        System.err.println("No event found with name: " + name);
        return Optional.empty();
*/    }

    public List<Event> getAll() {
        return events;
    }

    public Event updateEvent(Event request, String name) {
        var findEvent = getEventByName(name);

        Event eventToUpdate = findEvent.get();
        eventToUpdate.setName(request.getName());
        eventToUpdate.setLocation(request.getLocation());
        eventToUpdate.setDate(request.getDate());

        return eventToUpdate;
    }
}
