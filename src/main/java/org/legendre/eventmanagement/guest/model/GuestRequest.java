package org.legendre.eventmanagement.guest.model;

import lombok.Data;

@Data
public class GuestRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
}
