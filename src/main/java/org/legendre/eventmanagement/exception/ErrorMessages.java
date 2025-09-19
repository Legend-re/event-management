package org.legendre.eventmanagement.exception;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    //Not found messages
    RECORD_NOT_FOUND("Record not found"),
    EVENT_NOT_FOUND("Event not found"),
    HOST_NOT_FOUND("Host not found"),
    USER_NOT_FOUND("User not found"),
    GUEST_NOT_FOUND("Guest not found"),
    TICKET_NOT_FOUND("Ticket not found"),

    RECORD_ALREADY_EXIST("Record already exist"),
    EVENT_ALREADY_EXIST("Event already exist"),
    HOST_ALREADY_EXIST("Host already exist"),
    USER_ALREADY_EXIST("User already exist"),
    GUEST_ALREADY_EXIST("Guest already exist"),
    TICKET_ALREADY_EXIST("Ticket already exist"),
    PASSWORD_MISMATCH("Password mismatch"),

    TICKET_SOLD_OUT("Ticket sold out for requested event");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
