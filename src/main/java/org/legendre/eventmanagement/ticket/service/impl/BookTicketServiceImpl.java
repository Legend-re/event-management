package org.legendre.eventmanagement.ticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.event.service.EventService;
import org.legendre.eventmanagement.exception.ErrorCode;
import org.legendre.eventmanagement.exception.ErrorResponse;
import org.legendre.eventmanagement.exception.RecordNotFoundException;
import org.legendre.eventmanagement.guest.service.GuestService;
import org.legendre.eventmanagement.ticket.TicketStatus;
import org.legendre.eventmanagement.ticket.model.BookTicket;
import org.legendre.eventmanagement.ticket.model.BookTicketRequest;
import org.legendre.eventmanagement.ticket.model.repository.BookTicketRepository;
import org.legendre.eventmanagement.ticket.service.BookTicketService;
import org.legendre.eventmanagement.ticket.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.legendre.eventmanagement.exception.ErrorMessages.*;

@Service
@RequiredArgsConstructor
public class BookTicketServiceImpl implements BookTicketService {

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

    @Override
    public BookTicket bookTicket(BookTicketRequest request) {
        var ticketId = generateTicketId();

        var findGuest = guestService.getGuestByEmail(request.getGuestEmail())
                .orElseThrow(() -> new RecordNotFoundException(
                        new ErrorResponse(GUEST_NOT_FOUND.getMessage(), ErrorCode.RSC01))
                );

        var findTicketByEventName = ticketService.getTicketByEventName(request.getEventName())
                .orElseThrow(() -> new RecordNotFoundException(
                                new ErrorResponse(TICKET_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                        )
                );

        if (findTicketByEventName.getTicketsLeft() == 0)
            throw new RecordNotFoundException(
                    new ErrorResponse(TICKET_SOLD_OUT.getMessage(), ErrorCode.RSC01)
            );

        var findEvent = eventService.getEventByName(request.getEventName())
                .orElseThrow(() -> new RecordNotFoundException(
                        new ErrorResponse(EVENT_NOT_FOUND.getMessage(), ErrorCode.RSC01)));

        findTicketByEventName.setTotalTicketsSold(findTicketByEventName.getTotalTicketsSold() + 1);
        findTicketByEventName.setTicketsLeft(findTicketByEventName.getTicketsLeft() - 1);

        return bookTicketRepository.save(
                BookTicket.builder()
                        .ticketId(ticketId)
                        .ticketStatus(TicketStatus.SUCCESS)
                        .guest(findGuest)
                        .event(findEvent).build());
    }

    @Override
    public List<BookTicket> getAllBookedTickets() {
        return bookTicketRepository.findAll();
    }

    @Override
    public List<BookTicket> getTicketsBookedByGuest(String guestEmail) {
        var findGuest = guestService.getGuestByEmail(guestEmail)
                .orElseThrow(() -> new RecordNotFoundException(
                        new ErrorResponse(GUEST_NOT_FOUND.getMessage(), ErrorCode.RSC01)
                ));

        return bookTicketRepository.findByGuest(findGuest);
    }

    @Override
    public Optional<BookTicket> getTicketByTicketId(String ticketId) {
        return bookTicketRepository.findByTicketId(ticketId);
    }
}