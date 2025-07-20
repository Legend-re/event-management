package org.legendre.eventmanagement.guest.service;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.guest.model.Guest;
import org.legendre.eventmanagement.guest.model.GuestRequest;
import org.legendre.eventmanagement.guest.model.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

    public Guest createGuest(GuestRequest request) {
        guestRepository.findByEmailAddress(request.getEmailAddress())
                .ifPresent(guest -> {
                    throw new IllegalArgumentException("Email address already in use");
                });

        return guestRepository.save(
                Guest.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .emailAddress(request.getEmailAddress())
                        .phoneNumber(request.getPhoneNumber()).build());
    }

    public Optional<Guest> getGuestByEmail(String email) {
        return Optional.ofNullable(guestRepository.findByEmailAddress(email)
                .orElseThrow(() -> new IllegalArgumentException("Guest with queried email not found")));
    }

    public List<Guest> getAll() {
        return guestRepository.findAll();
    }

    public Guest updateGuest(GuestRequest request, String email) {
        var findGuest = guestRepository.findByEmailAddress(email).orElseThrow(
                () -> new IllegalArgumentException("Email address not found")
        );

        guestRepository.findByEmailAddress(request.getEmailAddress())
                .ifPresent(guest -> {
                    throw new IllegalArgumentException("Email address not found");
                });

        assert findGuest != null;
        return guestRepository.save(
                findGuest.toBuilder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .emailAddress(request.getEmailAddress())
                .phoneNumber(request.getPhoneNumber()).build());
    }

    public void deleteGuest(String email) {
        guestRepository.findByEmailAddress(email).ifPresent(guestRepository::delete);
    }
}