package org.legendre.eventmanagement.api;

public interface APIs {


    //module urls
    String EVENT_URL = "/api/v1/ent-mng/event";

    //paths
    String CREATE_EVENT_PATH = "/create";
    String UPDATE_EVENT_PATH = "/update";

    //path variables
    String GET_EVENT_PATH_VARIABLE = "/{eventName}";

}
