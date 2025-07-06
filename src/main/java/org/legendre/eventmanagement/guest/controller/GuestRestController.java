package org.legendre.eventmanagement.guest.controller;

import org.legendre.eventmanagement.guest.Guest;
import org.legendre.eventmanagement.guest.GuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.api.APIs.*;

@RestController
@RequestMapping(GUEST_URL)
public class GuestRestController {
    private final GuestService guestService;

    public GuestRestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping(CREATE_PATH)
    private Guest createGuest(@RequestBody Guest request) {
        return guestService.createGuest(request);
    }

    @GetMapping(GET_PATH)
    private Optional<Guest> getGuest(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return guestService.getGuestByEmail(name);
    }

    @GetMapping
    private List<Guest> getGuest() {
        return guestService.getAll();
    }

    @PutMapping(UPDATE_PATH)
    private Guest updateGuest(@RequestBody Guest request, @RequestParam String name) {
        return guestService.updateGuest(request, name);
    }

    @DeleteMapping(DELETE_PATH)
    private Optional<Guest> deleteGuest(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String name) {
        return guestService.deleteGuest(name);

    }

}
