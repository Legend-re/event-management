package org.legendre.eventmanagement.ticket.model.repository;

import org.legendre.eventmanagement.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByEventName(String name);
}
