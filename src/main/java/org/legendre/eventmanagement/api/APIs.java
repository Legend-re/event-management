package org.legendre.eventmanagement.api;

public interface APIs {

    //module urls
    String EVENT_URL = "/api/v1/ent-mng/event";
    String HOST_URL = "/api/v1/ent-mng/host";
    String USER_URL = "/api/v1/ent-mng";
    String GUEST_URL = "/api/v1/ent-mng/guest";
    String TICKET_URL = "/api/v1/ent-mng/ticket";
    String BOOK_TICKET_URL = "/api/v1/ent-mng/book-ticket";

    //paths
    String SIGNUP_PATH = "/sign-up";
    String CHANGE_PASSWORD_PATH = "/change-password";
    String CREATE_PATH = "/create";
    String UPDATE_PATH = "/update";
    String GET_PATH = "/get/{name}";
    String GET_BY_ID_PATH = "/get/{ticketId}";
    String GET_EMAIL_PATH = "/get/{email}";
    String DELETE_PATH = "/delete/{name}";

    //path variables
    String GET_BY_NAME_PATH_VARIABLE = "name";
    String GET_BY_ID_PATH_VARIABLE = "ticketId";
    String GET_BY_EMAIL_PATH_VARIABLE = "email";
}
