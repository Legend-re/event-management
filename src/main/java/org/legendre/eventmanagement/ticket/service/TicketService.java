package org.legendre.eventmanagement.ticket.service;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.event.service.EventService;
import org.legendre.eventmanagement.guest.service.GuestService;
import org.legendre.eventmanagement.ticket.TicketStatus;
import org.legendre.eventmanagement.ticket.model.BookTicket;
import org.legendre.eventmanagement.ticket.model.BookTicketRequest;
import org.legendre.eventmanagement.ticket.model.Ticket;
import org.legendre.eventmanagement.ticket.model.repository.BookTicketRepository;
import org.legendre.eventmanagement.ticket.model.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    private final BookTicketRepository bookTicketRepository;

    private final EventService event;

    private final GuestService guest;

    private static final String PREFIX = "TI";


    public Ticket createTicket(Ticket request) {
        return ticketRepository.save(new Ticket(request.getId(),request.getTotalTickets(), 0, request.getTotalTickets(), request.getTicketsLeft(), request.getEventName()));
    }

    public Optional<Ticket> getTicketByEventName(String name) {
        return ticketRepository.findByEventName(name);
    }

    public Optional<BookTicket> getTicketById(String ticketId) {
        return bookTicketRepository.findById(ticketId);
    }

    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    public Optional<BookTicket> getNumberOfTicketsBookedByGuest(String guestEmail) {
        return bookTicketRepository.findByEmailAddress(guestEmail);
    }


    public Ticket updateTicket(Ticket request, String name) {
        var findTicket = ticketRepository.findByEventName(name).orElse(null);

        assert findTicket != null;
        findTicket.setEventName(request.getEventName());
        findTicket.setTotalTickets(request.getTotalTickets());
        return findTicket;
    }

    public void deleteTicket(String name) {
        ticketRepository.findByEventName(name).ifPresent(ticketRepository::delete);
    }

    //Booking Tickets starts here
    public static String generateTicketId() {
        StringBuilder flightNumber = new StringBuilder(PREFIX);

        int randomNumber = new Random().nextInt(9999) + 1;
        flightNumber.append(String.format("%04d", randomNumber));

        return String.valueOf(flightNumber);
    }

    public BookTicket bookTicket(BookTicketRequest request) {
        var ticketId = generateTicketId();

        var findGuest = guest.getGuestByEmail(request.getGuestEmail()).orElse(null);
        assert findGuest != null;

//        StringBuilder sb = new StringBuilder();
//        sb.append(findGuest.getFirstName());
//        sb.append(" ");
//        sb.append(findGuest.getLastName());

        var findTicketByEventName = getTicketByEventName(request.getEventName())
                .orElseThrow(() -> new IllegalArgumentException("Tickets Are not available for this event: " + request.getEventName()));

        if (findTicketByEventName.getTicketsLeft() == 0)
            throw new RuntimeException("Tickets are sold out for this event: " + request.getEventName());

        var findEvent = event.getEventByName(
                request.getEventName()).orElse(null);
        assert findEvent != null;

        var bookNewTicket = new BookTicket();
        bookNewTicket.setTicketId(ticketId);
        bookNewTicket.setTicketStatus(TicketStatus.SUCCESS);
//        bookNewTicket.setGuestFullName(String.valueOf(sb));
        bookNewTicket.setGuest(findGuest);
        bookNewTicket.setEvent(findEvent);

        findTicketByEventName.setTotalTicketsSold(findTicketByEventName.getTotalTicketsSold() + 1);
        findTicketByEventName.setTicketsLeft(findTicketByEventName.getTicketsLeft() - 1);

        bookedTickets.add(bookNewTicket);
        return bookNewTicket;
    }

    public List<BookTicket> getAllBookedTickets() {
        return bookedTickets;
    }
}