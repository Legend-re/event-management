package org.legendre.eventmanagement.ticket.model;

import lombok.*;

@Data
public class TicketRequest {
    private int totalTickets;
    private String eventName;
}