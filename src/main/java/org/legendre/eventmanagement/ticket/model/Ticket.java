package org.legendre.eventmanagement.ticket.model;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalTickets;
    private int totalTicketsSold;
    private int ticketsLeft;
    private String eventName;
}