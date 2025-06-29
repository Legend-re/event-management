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
    String DELETE_PATH = "/delete";

    //path variables
    String GET_BY_NAME_PATH_VARIABLE = "/{name}";

}
