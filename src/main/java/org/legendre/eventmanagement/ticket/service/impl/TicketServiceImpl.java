package org.legendre.eventmanagement.ticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.event.service.impl.EventServiceImpl;
import org.legendre.eventmanagement.exception.ErrorCode;
import org.legendre.eventmanagement.exception.ErrorResponse;
import org.legendre.eventmanagement.exception.RecordNotFoundException;
import org.legendre.eventmanagement.ticket.model.Ticket;
import org.legendre.eventmanagement.ticket.model.TicketRequest;
import org.legendre.eventmanagement.ticket.model.repository.TicketRepository;
import org.legendre.eventmanagement.ticket.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.exception.ErrorMessages.EVENT_NOT_FOUND;
import static org.legendre.eventmanagement.exception.ErrorMessages.TICKET_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final EventServiceImpl eventService;

    @Override
    public Ticket createTicket(TicketRequest request) {
        var findEvent = eventService.getEventByName(request.getEventName())
                .orElseThrow(() -> new RecordNotFoundException(
                        new ErrorResponse(EVENT_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                ));

        return ticketRepository.save(Ticket.builder()
                .eventName(findEvent.getName())
                .totalTickets(request.getTotalTickets())
                .totalTicketsSold(0)
                .ticketsLeft(request.getTotalTickets()).build());
    }

    @Override
    public Optional<Ticket> getTicketByEventName(String name) {
        return Optional.ofNullable(ticketRepository.findByEventName(name)
                .orElseThrow(() -> new RecordNotFoundException(
                        new ErrorResponse(TICKET_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                ))
        );
    }

    @Override
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket updateTicket(TicketRequest request) {
        var findTicket = ticketRepository.findByEventName(request.getEventName())
                .orElseThrow(() -> new RecordNotFoundException(
                        new ErrorResponse(TICKET_NOT_FOUND.getMessage(), ErrorCode.RSC01))
                );

        findTicket.setEventName(request.getEventName());
        findTicket.setTotalTickets(request.getTotalTickets());
        return ticketRepository.save(
                findTicket.toBuilder()
                        .totalTickets(findTicket.getTotalTickets() + request.getTotalTickets())
                        .ticketsLeft(findTicket.getTicketsLeft() + request.getTotalTickets()).build());
    }

    @Override
    public void deleteTicket(String name) {
        ticketRepository.findByEventName(name).ifPresent(ticketRepository::delete);
    }
}