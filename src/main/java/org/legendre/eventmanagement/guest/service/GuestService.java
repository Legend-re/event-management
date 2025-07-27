package org.legendre.eventmanagement.guest.service;

import org.legendre.eventmanagement.guest.model.Guest;
import org.legendre.eventmanagement.guest.model.GuestRequest;

import java.util.List;
import java.util.Optional;

public interface GuestService {
    Guest createGuest(GuestRequest request);

    Optional<Guest> getGuestByEmail(String email);

    List<Guest> getAll();

    Guest updateGuest(GuestRequest request, String email);

    void deleteGuest(String email);
}
