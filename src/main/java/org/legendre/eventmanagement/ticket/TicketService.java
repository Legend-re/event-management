package org.legendre.eventmanagement.ticket;

import org.legendre.eventmanagement.event.service.EventService;
import org.legendre.eventmanagement.guest.GuestService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TicketService {

    private final List<Ticket> tickets = new ArrayList<>();

    private final List<BookTicket> bookedTickets = new ArrayList<>();

    private final EventService event;

    private final GuestService guest;

    private static final String PREFIX = "TI";

    public TicketService(EventService event, GuestService guest) {
        this.event = event;
        this.guest = guest;
    }

    public Ticket createTicket(Ticket request) {
        var findEventIfExists = event.getEventByName(request.getEventName()).orElse(null);

        assert findEventIfExists != null;
        var newTicket = new Ticket(request.getTotalTickets(), 0, request.getTotalTickets(), findEventIfExists.getName());
        tickets.add(newTicket);
        return newTicket;
    }

    public Optional<Ticket> getTicketByEventName(String name) {
        var foundTicket = tickets.stream().filter(ticket -> ticket.getEventName().equalsIgnoreCase(name))
                .findFirst();
        foundTicket.ifPresentOrElse((
                        ticket -> System.out.println("Found ticket: " + name)),
                () -> System.err.println("No ticket found with name: " + name));
        return foundTicket;
    }

    public List<Ticket> getAll() {
        return tickets;
    }

    public Ticket updateTicket(Ticket request, String name) {
        var findTicket = getTicketByEventName(name);

        Ticket ticketToUpdate = findTicket.get();
        ticketToUpdate.setEventName(request.getEventName());
        ticketToUpdate.setTotalTickets(request.getTotalTickets());
        return ticketToUpdate;
    }

    public Optional<Ticket> deleteTicket(String name) {
        var ticketToDelete = getTicketByEventName(name);

        ticketToDelete.ifPresentOrElse(host -> {
            tickets.remove(ticketToDelete.get());
            System.out.println("Deleted ticket: " + name);
        }, () -> System.err.println("No ticket found for required event: " + name));
        return ticketToDelete;
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