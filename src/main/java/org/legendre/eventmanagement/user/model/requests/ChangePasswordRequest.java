package org.legendre.eventmanagement.user.model.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(@NotBlank(message = "email is required") @Email String username,
                                    @NotBlank(message = "oldPassword is required") String oldPassword,
                                    @NotBlank(message = "newPassword is required") String newPassword,
                                    @NotBlank(message = "confirmPassword is required") String confirmPassword) {
}
