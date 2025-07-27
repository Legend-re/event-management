package org.legendre.eventmanagement.exception;

public enum ErrorMessages {

    //Not found messages
    RECORD_NOT_FOUND("Record not found", "01"),
    EVENT_NOT_FOUND("Event not found", "01"),
    HOST_NOT_FOUND("Host not found", "01"),
    GUEST_NOT_FOUND("Guest not found", "01"),
    TICKET_NOT_FOUND("Ticket not found", "01"),

    RECORD_ALREADY_EXIST("Record already exist", "02"),
    EVENT_ALREADY_EXIST("Event already exist", "02"),
    HOST_ALREADY_EXIST("Host already exist", "02"),
    GUEST_ALREADY_EXIST("Guest already exist", "02"),
    TICKET_ALREADY_EXIST("Ticket already exist", "02"),

    TICKET_SOLD_OUT("Ticket sold out for requested event", "02");



    private String message;
    private String code;
    ErrorMessages(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
