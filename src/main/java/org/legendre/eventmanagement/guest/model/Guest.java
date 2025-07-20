package org.legendre.eventmanagement.guest.model;

import jakarta.persistence.*;
import lombok.*;
import org.legendre.eventmanagement.ticket.model.BookTicket;
import org.legendre.eventmanagement.ticket.model.Ticket;

import java.util.List;

@Table
@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    @OneToMany
    @ToString.Exclude
    private List<BookTicket> bookedTickets;
}
