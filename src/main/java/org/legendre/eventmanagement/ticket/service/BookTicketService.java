package org.legendre.eventmanagement.ticket.service;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.event.service.EventService;
import org.legendre.eventmanagement.guest.service.GuestService;
import org.legendre.eventmanagement.ticket.TicketStatus;
import org.legendre.eventmanagement.ticket.model.BookTicket;
import org.legendre.eventmanagement.ticket.model.BookTicketRequest;
import org.legendre.eventmanagement.ticket.model.Ticket;
import org.legendre.eventmanagement.ticket.model.TicketRequest;
import org.legendre.eventmanagement.ticket.model.repository.BookTicketRepository;
import org.legendre.eventmanagement.ticket.model.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookTicketService {

    private final BookTicketRepository bookTicketRepository;

    private final EventService eventService;

    private final GuestService guestService;

    private final TicketService ticketService;

    private static final String PREFIX = "TI";

    public static String generateTicketId() {
        StringBuilder flightNumber = new StringBuilder(PREFIX);

        int randomNumber = new Random().nextInt(9999) + 1;
        flightNumber.append(String.format("%04d", randomNumber));

        return String.valueOf(flightNumber);
    }

    public BookTicket bookTicket(BookTicketRequest request) {
        var ticketId = generateTicketId();

        var findGuest = guestService.getGuestByEmail(request.getGuestEmail())
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));

//        StringBuilder sb = new StringBuilder();
//        sb.append(findGuest.getFirstName());
//        sb.append(" ");
//        sb.append(findGuest.getLastName());

        var findTicketByEventName = ticketService.getTicketByEventName(request.getEventName())
                .orElseThrow(() -> new IllegalArgumentException("Tickets are not available for this event: " + request.getEventName()));

        if (findTicketByEventName.getTicketsLeft() == 0)
            throw new RuntimeException("Tickets are sold out for this event: " + request.getEventName());

        var findEvent = eventService.getEventByName(request.getEventName())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        findTicketByEventName.setTotalTicketsSold(findTicketByEventName.getTotalTicketsSold() + 1);
        findTicketByEventName.setTicketsLeft(findTicketByEventName.getTicketsLeft() - 1);

        return bookTicketRepository.save(
                BookTicket.builder()
                        .ticketId(ticketId)
                        .ticketStatus(TicketStatus.SUCCESS)
                        .guest(findGuest)
                        .event(findEvent).build());
    }

    public List<BookTicket> getAllBookedTickets() {
        return bookTicketRepository.findAll();
    }

    public List<BookTicket> getTicketsBookedByGuest(String guestEmail) {
        var findGuest = guestService.getGuestByEmail(guestEmail)
                .orElseThrow(() -> new IllegalArgumentException("Tickets not found"));

        return bookTicketRepository.findByGuest(findGuest);
    }

    public Optional<BookTicket> getTicketByTicketId(String ticketId) {
        return bookTicketRepository.findByTicketId(ticketId);
    }
}