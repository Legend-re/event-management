package org.legendre.eventmanagement.host.model;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String bio;

}
