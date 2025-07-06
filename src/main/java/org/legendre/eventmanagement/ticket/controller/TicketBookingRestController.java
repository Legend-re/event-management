package org.legendre.eventmanagement.ticket.controller;

import org.legendre.eventmanagement.ticket.BookTicket;
import org.legendre.eventmanagement.ticket.BookTicketRequest;
import org.legendre.eventmanagement.ticket.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.legendre.eventmanagement.api.APIs.BOOK_TICKET_PATH;
import static org.legendre.eventmanagement.api.APIs.TICKET_URL;

@RestController
@RequestMapping(TICKET_URL)
public class TicketBookingRestController {

    private final TicketService ticketService;

    public TicketBookingRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(BOOK_TICKET_PATH)
    private BookTicket bookTicket(@RequestBody BookTicketRequest request) {
        return ticketService.bookTicket(request);
    }

    @GetMapping(BOOK_TICKET_PATH)
    private List<BookTicket> getAll() {
        return ticketService.getAllBookedTickets();
    }


}