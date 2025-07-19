package org.legendre.eventmanagement.ticket.model;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Getter
@Setter
public class BookTicketRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String guestEmail;
    private String eventName;
}
