package org.legendre.eventmanagement.ticket.model;

import jakarta.validation.constraints.NotBlank;

public record BookTicketRequest(
        @NotBlank(message = "guestEmail is required") String guestEmail,
        @NotBlank(message = "eventName is required") String eventName
) {
}
