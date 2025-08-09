package org.legendre.eventmanagement.host.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record HostRequest (
        @NotBlank(message = "name is required") String name,
        @Email(message = "email has invalid pattern", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        @NotBlank(message = "email cannot be null") String email,
        @NotBlank(message = "bio cannot be null") String bio
){
}
