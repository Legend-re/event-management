package org.legendre.eventmanagement.ticket.model;

import jakarta.persistence.*;
import lombok.*;
import org.legendre.eventmanagement.event.model.Event;
import org.legendre.eventmanagement.guest.model.Guest;
import org.legendre.eventmanagement.ticket.TicketStatus;
@Table
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ticketId;
    private TicketStatus ticketStatus;
    private Guest guest;
    private Event event;
}
