package org.legendre.eventmanagement.guest.service.impl;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.exception.DuplicateRecordException;
import org.legendre.eventmanagement.exception.ErrorCode;
import org.legendre.eventmanagement.exception.ErrorResponse;
import org.legendre.eventmanagement.exception.RecordNotFoundException;
import org.legendre.eventmanagement.guest.model.Guest;
import org.legendre.eventmanagement.guest.model.GuestRequest;
import org.legendre.eventmanagement.guest.model.repository.GuestRepository;
import org.legendre.eventmanagement.guest.service.GuestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.exception.ErrorMessages.GUEST_ALREADY_EXIST;
import static org.legendre.eventmanagement.exception.ErrorMessages.GUEST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepository;

    @Override
    public Guest createGuest(GuestRequest request) {
        guestRepository.findByEmailAddress(request.getEmailAddress())
                .ifPresent(guest -> {
                    throw new DuplicateRecordException(
                            new ErrorResponse(GUEST_ALREADY_EXIST.getMessage(), ErrorCode.RSC02));
                });

        return guestRepository.save(
                Guest.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .emailAddress(request.getEmailAddress())
                        .phoneNumber(request.getPhoneNumber()).build());
    }

    @Override
    public Optional<Guest> getGuestByEmail(String email) {
        return Optional.ofNullable(guestRepository.findByEmailAddress(email)
                .orElseThrow(() -> new RecordNotFoundException(
                        new ErrorResponse(GUEST_NOT_FOUND.getMessage(), ErrorCode.RSC01))));
    }

    @Override
    public List<Guest> getAll() {
        return guestRepository.findAll();
    }

    @Override
    public Guest updateGuest(GuestRequest request, String email) {
        var findGuest = guestRepository.findByEmailAddress(email).
                orElseThrow(() -> new RecordNotFoundException(
                        new ErrorResponse(GUEST_NOT_FOUND.getMessage(), ErrorCode.RSC01))
                );

        guestRepository.findByEmailAddress(request.getEmailAddress())
                .ifPresent(guest -> {
                    throw new DuplicateRecordException(
                            new ErrorResponse(GUEST_ALREADY_EXIST.getMessage(), ErrorCode.RSC02));
                });

        assert findGuest != null;
        return guestRepository.save(
                findGuest.toBuilder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .emailAddress(request.getEmailAddress())
                        .phoneNumber(request.getPhoneNumber()).build());
    }

    @Override
    public void deleteGuest(String email) {
        guestRepository.findByEmailAddress(email).ifPresent(guestRepository::delete);
    }
}