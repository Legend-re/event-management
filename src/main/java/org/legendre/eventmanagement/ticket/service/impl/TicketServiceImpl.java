package org.legendre.eventmanagement.ticket.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final EventServiceImpl eventService;

    @Override
    public Ticket createTicket(TicketRequest request) {
        log.info("Creating ticket: {}", request.eventName());
        var findEvent = eventService.getEventByName(request.eventName())
                .orElseThrow(() -> {log.error("Guest not found: {}", request.eventName());
                        return new RecordNotFoundException(
                        new ErrorResponse(EVENT_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                );});

        var savedTicket = ticketRepository.save(Ticket.builder()
                .eventName(findEvent.getName())
                .totalTickets(request.totalTickets())
                .totalTicketsSold(0)
                .ticketsLeft(request.totalTickets()).build());
        log.info("Ticket created successfully: {}", savedTicket.getTicketsLeft());
        return savedTicket;
    }

    @Override
    public Optional<Ticket> getTicketByEventName(String name) {
        log.info("Fetching ticket by event name: {}", name);
        return Optional.ofNullable(ticketRepository.findByEventName(name)
                .orElseThrow(() -> {log.error("Ticket not found: {}", name);
                        return new RecordNotFoundException(
                        new ErrorResponse(TICKET_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                );})
        );
    }

    @Override
    public List<Ticket> getAll() {
        log.info("Fetching all tickets");
        return ticketRepository.findAll();
    }

    @Override
    public Ticket updateTicket(TicketRequest request) {
        log.info("Updating ticket: {}", request.eventName());
        var findTicket = ticketRepository.findByEventName(request.eventName())
                .orElseThrow(() -> {log.error("Ticket not found: {}", request.eventName());
                        return new RecordNotFoundException(
                        new ErrorResponse(TICKET_NOT_FOUND.getMessage(), ErrorCode.RSC01));}
                );

        findTicket.setEventName(request.eventName());
        findTicket.setTotalTickets(request.totalTickets());

        Ticket updatedTicket = ticketRepository.save(
                findTicket.toBuilder()
                        .totalTickets(findTicket.getTotalTickets() + request.totalTickets())
                        .ticketsLeft(findTicket.getTicketsLeft() + request.totalTickets()).build());
        log.info("Ticket updated successfully: {}", updatedTicket.getTicketsLeft());
        return updatedTicket;
    }

    @Override
    public void deleteTicket(String name) {
        log.info("Deleting ticket: {}", name);
        ticketRepository.findByEventName(name).ifPresent(ticketRepository::delete);
        log.info("Ticket deleted successfully: {}", name);
    }
}