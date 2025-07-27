package org.legendre.eventmanagement.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DuplicateRecordException extends RuntimeException {
    ErrorResponse errorResponse;

    public DuplicateRecordException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}