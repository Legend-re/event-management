package org.legendre.eventmanagement.guest.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record GuestRequest(
        @NotBlank(message = "firstName is required") String firstName,
        @NotBlank(message = "lastName is required") String lastName,
        @Pattern(message = "phoneNumber is invalid", regexp = "^(?:\\+234|0)[789]0[1-9]\\d{8}$")
        @NotBlank(message = "phoneNumber is required") String phoneNumber,
        @NotBlank(message = "emailAddress is required") String emailAddress
) {
}
