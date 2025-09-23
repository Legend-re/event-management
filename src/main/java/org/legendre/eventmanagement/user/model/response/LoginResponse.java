package org.legendre.eventmanagement.user.model.response;

import java.util.Date;

public record LoginResponse(String username, String token, Date expiryAt) {
}
