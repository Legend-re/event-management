package org.legendre.eventmanagement.guest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepository;

    @Override
    public Guest createGuest(GuestRequest request) {
        log.info("creating a guest: {}", request.getEmailAddress());
        guestRepository.findByEmailAddress(request.getEmailAddress())
                .ifPresent(guest -> {log.error("Guest already exist: {}", request.getEmailAddress());
                    throw new DuplicateRecordException(
                            new ErrorResponse(GUEST_ALREADY_EXIST.getMessage(), ErrorCode.RSC02));
                });

        var savedGuest = guestRepository.save(
                Guest.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .emailAddress(request.getEmailAddress())
                        .phoneNumber(request.getPhoneNumber()).build());
        log.info("Guest created successfully: {}", savedGuest.getFirstName());
        return savedGuest;
    }

    @Override
    public Optional<Guest> getGuestByEmail(String email) {
        log.info("Fetching guest by email: {}", email);
        return Optional.ofNullable(guestRepository.findByEmailAddress(email)
                .orElseThrow(() -> {log.error("Guest not found: {}", email);
                        return new RecordNotFoundException(
                        new ErrorResponse(GUEST_NOT_FOUND.getMessage(), ErrorCode.RSC01));}
                ));
    }

    @Override
    public List<Guest> getAll() {
        log.info("Fetching all guests");
        return guestRepository.findAll();
    }

    @Override
    public Guest updateGuest(GuestRequest request, String email) {
        log.info("Updating a guest: {}", email);
        var findGuest = guestRepository.findByEmailAddress(email).
                orElseThrow(() -> {log.error("Guest not found: {}", email);
                        return new RecordNotFoundException(
                        new ErrorResponse(GUEST_NOT_FOUND.getMessage(), ErrorCode.RSC01));}
                );

        guestRepository.findByEmailAddress(request.getEmailAddress())
                .ifPresent(guest -> {
                    throw new DuplicateRecordException(
                            new ErrorResponse(GUEST_ALREADY_EXIST.getMessage(), ErrorCode.RSC02));
                });

        assert findGuest != null;
        Guest savedGuest = guestRepository.save(
                findGuest.toBuilder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .emailAddress(request.getEmailAddress())
                        .phoneNumber(request.getPhoneNumber()).build());
        log.info("Guest updated successfully: {}", savedGuest.getFirstName());
        return savedGuest;
    }

    @Override
    public void deleteGuest(String email) {
        log.info("Deleting a guest: {}", email);
        guestRepository.findByEmailAddress(email).ifPresent(guestRepository::delete);
        log.info("Guest deleted successfully: {}", email);
    }
}