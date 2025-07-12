package org.legendre.eventmanagement.api;

public interface APIs {


    //module urls
    String EVENT_URL = "/api/v1/ent-mng/event";
    String HOST_URL = "/api/v1/ent-mng/host";
    String GUEST_URL = "/api/v1/ent-mng/guest";
    String TICKET_URL = "/api/v1/ent-mng/ticket";

    //paths
    String CREATE_PATH = "/create";
    String UPDATE_PATH = "/update";
    String GET_PATH = "/get/{name}";
    String GETBYID_PATH = "/get/{ticketId}";
    String GETBYEMAIL_PATH = "/get/{email}";
    String DELETE_PATH = "/delete/{name}";
    String BOOK_TICKET_PATH = "/book-ticket";

    //path variables
    String GET_BY_NAME_PATH_VARIABLE = "name";
    String GET_BY_ID_PATH_VARIABLE = "ticketId";
    String GET_BY_EMAIL_PATH_VARIABLE = "email";

}
