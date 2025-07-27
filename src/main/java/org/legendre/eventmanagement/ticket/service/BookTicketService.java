package org.legendre.eventmanagement.ticket.service;

import org.legendre.eventmanagement.ticket.model.BookTicket;
import org.legendre.eventmanagement.ticket.model.BookTicketRequest;

import java.util.List;
import java.util.Optional;

public interface BookTicketService {
    BookTicket bookTicket(BookTicketRequest request);

    List<BookTicket> getAllBookedTickets();

    List<BookTicket> getTicketsBookedByGuest(String guestEmail);

    Optional<BookTicket> getTicketByTicketId(String ticketId);
}
