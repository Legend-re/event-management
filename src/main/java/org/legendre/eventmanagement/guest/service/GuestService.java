package org.legendre.eventmanagement.guest.service;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.guest.model.Guest;
import org.legendre.eventmanagement.guest.model.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

    public Guest createGuest(Guest request) {

        return guestRepository.save(
                new Guest(request.getId(), request.getFirstName(), request.getLastName(), request.getPhoneNumber(), request.getEmailAddress()));
    }


    public Optional<Guest> getGuestByEmail(String email) {
        return guestRepository.findByEmailAddress(email);
    }

    public List<Guest> getAll() {
        return guestRepository.findAll();
    }

    public Guest updateGuest(Guest request, String email) {
        var findGuest = guestRepository.findByEmailAddress(email).orElse(null);

        assert findGuest != null;
        findGuest.setFirstName(request.getFirstName());
        findGuest.setLastName(request.getLastName());
        findGuest.setPhoneNumber(request.getPhoneNumber());
        findGuest.setEmailAddress(request.getEmailAddress());
        return findGuest;
    }

    public void deleteGuest(String email){
        guestRepository.findByEmailAddress(email).ifPresent(guestRepository::delete);
    }
}
