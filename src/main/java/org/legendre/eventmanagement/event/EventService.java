package org.legendre.eventmanagement.event;

import org.springframework.stereotype.Service;

@Service
public class EventService {

    public Event createEvent(Event request) {
        return new Event(request.getName(), request.getLocation(), request.getDate());
    }
}
