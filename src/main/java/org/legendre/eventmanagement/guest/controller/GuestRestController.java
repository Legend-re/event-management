package org.legendre.eventmanagement.guest.controller;

import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.guest.model.Guest;
import org.legendre.eventmanagement.guest.model.GuestRequest;
import org.legendre.eventmanagement.guest.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.api.APIs.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(GUEST_URL)
public class GuestRestController {

    private final GuestService guestService;

    @PostMapping(CREATE_PATH)
    private ResponseEntity<Guest> createGuest(@RequestBody GuestRequest request) {
        return new ResponseEntity<>(guestService.createGuest(request), HttpStatus.CREATED);
    }

    @GetMapping(GET_PATH)
    private ResponseEntity<Optional<Guest>> getGuest(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String email) {
        return new ResponseEntity<>(guestService.getGuestByEmail(email), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Guest>> getGuest() {
        return new ResponseEntity<>(guestService.getAll(), HttpStatus.OK);
    }

    @PutMapping(UPDATE_PATH)
    private ResponseEntity<Guest> updateGuest(@RequestBody GuestRequest request, @RequestParam String email) {
        return new ResponseEntity<>(guestService.updateGuest(request, email), HttpStatus.OK);
    }

    @DeleteMapping(DELETE_PATH)
    private ResponseEntity<Void> deleteGuest(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        guestService.deleteGuest(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
