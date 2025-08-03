package org.legendre.eventmanagement.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.legendre.eventmanagement.exception.ErrorMessages.RECORD_ALREADY_EXIST;
import static org.legendre.eventmanagement.exception.ErrorMessages.RECORD_NOT_FOUND;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public ErrorResponse handleRecordNotFoundException(RecordNotFoundException exception) {
        log.error("RecordNotFoundException occurred: {}", exception.getMessage());

        return (exception.getErrorResponse() != null) ?
                exception.getErrorResponse() :
                new ErrorResponse(RECORD_NOT_FOUND.getMessage(), ErrorCode.RSC01);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateRecordException.class)
    public ErrorResponse handleDuplicateRecordException(DuplicateRecordException exception) {
        log.error("DuplicateRecordException occurred: {}", exception.getMessage());

        return (exception.getErrorResponse() != null) ?
                exception.getErrorResponse() :
                new ErrorResponse(RECORD_ALREADY_EXIST.getMessage(), ErrorCode.RSC02);
    }
}
