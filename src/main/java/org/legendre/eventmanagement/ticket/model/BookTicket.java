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
@Builder(toBuilder = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticketId;
    private TicketStatus ticketStatus;
    @ManyToOne
    private Guest guest;
    @OneToOne
    private Event event;
}
