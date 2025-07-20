package org.legendre.eventmanagement.ticket.service;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.event.service.EventService;
import org.legendre.eventmanagement.ticket.model.Ticket;
import org.legendre.eventmanagement.ticket.model.TicketRequest;
import org.legendre.eventmanagement.ticket.model.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    private final EventService eventService;

    public Ticket createTicket(TicketRequest request) {
        var findEvent = eventService.getEventByName(request.getEventName())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        return ticketRepository.save(Ticket.builder()
                .eventName(findEvent.getName())
                .totalTickets(request.getTotalTickets())
                .totalTicketsSold(0)
                .ticketsLeft(request.getTotalTickets()).build());
    }

    public Optional<Ticket> getTicketByEventName(String name) {
        return Optional.ofNullable(ticketRepository.findByEventName(name)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found")));
    }

    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    public Ticket updateTicket(TicketRequest request) {
        var findTicket = ticketRepository.findByEventName(request.getEventName())
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        findTicket.setEventName(request.getEventName());
        findTicket.setTotalTickets(request.getTotalTickets());
        return ticketRepository.save(
                findTicket.toBuilder()
                        .totalTickets(findTicket.getTotalTickets() + request.getTotalTickets())
                        .ticketsLeft(findTicket.getTicketsLeft() + request.getTotalTickets()).build());
    }

    public void deleteTicket(String name) {
        ticketRepository.findByEventName(name).ifPresent(ticketRepository::delete);
    }
}