package org.legendre.eventmanagement.event.model;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record EventRequest(
        @NotBlank(message = "name is required") String name,
        @NotBlank(message = "name is required") String location,
        @NotBlank(message = "date is required") LocalDateTime date,
        @NotBlank(message = "hostName is required") String hostName
){
}