package org.legendre.eventmanagement.ticket.model;

import lombok.Data;

@Data
public class BookTicketRequest {
    private String guestEmail;
    private String eventName;
}
