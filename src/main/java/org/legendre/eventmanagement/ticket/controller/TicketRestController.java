package org.legendre.eventmanagement.ticket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.ticket.model.Ticket;
import org.legendre.eventmanagement.ticket.model.TicketRequest;
import org.legendre.eventmanagement.ticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.api.APIs.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(TICKET_URL)
public class TicketRestController {

    private final TicketService ticketService;

    @PostMapping(CREATE_PATH)
    private ResponseEntity<Ticket> createTicket(@RequestBody @Valid TicketRequest request) {
        return new ResponseEntity<>(ticketService.createTicket(request), HttpStatus.CREATED);
    }

    @GetMapping(GET_PATH)
    private ResponseEntity<Optional<Ticket>> getTicket(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return new ResponseEntity<>(ticketService.getTicketByEventName(name), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Ticket>> getTickets() {
        return new ResponseEntity<>(ticketService.getAll(), HttpStatus.OK);
    }

    @PutMapping(UPDATE_PATH)
    private ResponseEntity<Ticket> updateTicket(@RequestBody @Valid TicketRequest request) {
        return new ResponseEntity<>(ticketService.updateTicket(request), HttpStatus.OK);
    }

    @DeleteMapping(DELETE_PATH)
    private ResponseEntity<Void> deleteTicket(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        ticketService.deleteTicket(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}