package org.legendre.eventmanagement.ticket.model.repository;

import org.legendre.eventmanagement.guest.model.Guest;
import org.legendre.eventmanagement.ticket.model.BookTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookTicketRepository extends JpaRepository<BookTicket, Long> {
    Optional<BookTicket> findByTicketId(String ticketId);
    List<BookTicket> findByGuest(Guest guestEmail);
}
