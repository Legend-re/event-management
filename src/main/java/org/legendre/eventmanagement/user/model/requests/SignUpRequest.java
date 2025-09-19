package org.legendre.eventmanagement.user.model.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(@NotBlank(message = "firstName is required") String firstName,
                     @NotBlank(message = "lastName is required") String lastName,
                     @NotBlank(message = "email is required") @Email String email,
                     @NotBlank(message = "password is required") String password,
                     @NotBlank(message = "confirmPassword is required") String confirmPassword) {
}
