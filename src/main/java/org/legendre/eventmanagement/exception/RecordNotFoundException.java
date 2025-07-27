package org.legendre.eventmanagement.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecordNotFoundException extends RuntimeException {

    ErrorResponse errorResponse;

    public RecordNotFoundException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}