package org.legendre.eventmanagement.ticket.model.repository;

import org.legendre.eventmanagement.ticket.model.BookTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTicketRepository extends JpaRepository<BookTicket, String> {
    Optional<BookTicket> findById(String ticketId);
    Optional<BookTicket> findByEmailAddress(String guestEmail);
}
