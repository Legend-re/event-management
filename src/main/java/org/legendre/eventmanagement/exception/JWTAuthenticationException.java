package org.legendre.eventmanagement.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JWTAuthenticationException extends RuntimeException {
    ErrorResponse errorResponse;

    public JWTAuthenticationException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public JWTAuthenticationException(String message) {
        super(message);
    }
}