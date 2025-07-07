package org.legendre.eventmanagement.ticket.controller;

import org.legendre.eventmanagement.ticket.Ticket;
import org.legendre.eventmanagement.ticket.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.api.APIs.*;

@RestController
@RequestMapping(TICKET_URL)
public class TicketRestController {

    private final TicketService ticketService;

    public TicketRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(CREATE_PATH)
    private Ticket createHost(@RequestBody Ticket request) {
        return ticketService.createTicket(request);
    }

    @GetMapping(GET_PATH)
    private Optional<Ticket> getTicket(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return ticketService.getTicketByEventName(name);
    }

    @GetMapping
    private List<Ticket> getTickets() {
        return ticketService.getAll();
    }

    @PutMapping(UPDATE_PATH)
    private Ticket updateTicket(@RequestBody Ticket request, @RequestParam String name) {
        return ticketService.updateTicket(request, name);
    }

    @DeleteMapping(DELETE_PATH)
    private Optional<Ticket> deleteTicket(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return ticketService.deleteTicket(name);
    }

}
