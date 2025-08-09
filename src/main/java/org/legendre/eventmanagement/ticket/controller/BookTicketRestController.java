package org.legendre.eventmanagement.ticket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.ticket.model.BookTicket;
import org.legendre.eventmanagement.ticket.model.BookTicketRequest;
import org.legendre.eventmanagement.ticket.service.BookTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.api.APIs.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BOOK_TICKET_URL)
public class BookTicketRestController {

    private final BookTicketService bookTicketService;

    @PostMapping(CREATE_PATH)
    private ResponseEntity<BookTicket> bookTicket(@RequestBody @Valid BookTicketRequest request) {
        return new ResponseEntity<>(bookTicketService.bookTicket(request), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<BookTicket>> getAll() {
        return new ResponseEntity<>(bookTicketService.getAllBookedTickets(), HttpStatus.OK);
    }

    @GetMapping(GET_BY_ID_PATH)
    private ResponseEntity<Optional<BookTicket>> getByTicketId(@PathVariable(GET_BY_ID_PATH_VARIABLE) String ticketId) {
        return new ResponseEntity<>(bookTicketService.getTicketByTicketId(ticketId), HttpStatus.OK);
    }

    @GetMapping(GET_EMAIL_PATH)
    private ResponseEntity<List<BookTicket>> getTicketsByGuest(@PathVariable(GET_BY_EMAIL_PATH_VARIABLE) String email) {
        return new ResponseEntity<>(bookTicketService.getTicketsBookedByGuest(email), HttpStatus.OK);
    }
}