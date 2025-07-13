package org.legendre.eventmanagement.event.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private LocalDateTime date;
}