package org.legendre.eventmanagement.ticket.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketRequest(
        @NotNull(message = "totalTickets is required") Integer totalTickets,

        @NotBlank(message = "eventName is required") String eventName
) {
}