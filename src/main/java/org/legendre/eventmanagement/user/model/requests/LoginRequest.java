package org.legendre.eventmanagement.user.model.requests;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "firstName is required") String username,
        @NotBlank(message = "lastName is required") String password) {
}
