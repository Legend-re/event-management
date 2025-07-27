package org.legendre.eventmanagement.event.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventRequest {
    private String name;
    private String location;
    private LocalDateTime date;
    private String hostName;
}