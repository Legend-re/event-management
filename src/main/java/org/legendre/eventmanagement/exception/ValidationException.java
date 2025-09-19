package org.legendre.eventmanagement.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ValidationException extends RuntimeException {
    ErrorResponse errorResponse;

    public ValidationException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}