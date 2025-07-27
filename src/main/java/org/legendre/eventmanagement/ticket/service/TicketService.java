package org.legendre.eventmanagement.ticket.service;

import org.legendre.eventmanagement.ticket.model.Ticket;
import org.legendre.eventmanagement.ticket.model.TicketRequest;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    Ticket createTicket(TicketRequest request);

    Optional<Ticket> getTicketByEventName(String name);

    List<Ticket> getAll();

    Ticket updateTicket(TicketRequest request);

    void deleteTicket(String name);
}
