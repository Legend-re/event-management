package org.legendre.eventmanagement.guest;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GuestService {
    private final List<Guest> guests = new ArrayList<>();

    public Guest createGuest(Guest request) {

        var findByEmail = getGuestByEmail(request.getEmailAddress());
        if (findByEmail.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        guests.add(request);
        return new Guest(request.getFirstName(), request.getLastName(), request.getPhoneNumber(), request.getEmailAddress());
    }

    @PostConstruct
    public List<Guest> populateGuests() {
        guests.add(new Guest(
                "Maryam", "Jelili", "08082045211", "maryamjelili@gmail.com"));

        guests.add(new Guest(
                "Mubarak", "Ajia", "080811111111", "mubarakajia@gmail.com"));

        guests.add(new Guest(
                "Hadizah", "Muhammed", "07063298896", "hadizah@gmail.com"));
        return guests;
    }

    public Optional<Guest> getGuestByEmail(String email) {
        var foundGuest = guests.stream().filter(guest -> guest.getEmailAddress().equalsIgnoreCase(email))
                .findFirst();
        foundGuest.ifPresentOrElse((
                        guest -> System.out.println("Found guest: " + email)),
                () -> System.err.println("No guest found with name: " + email));

        return foundGuest;
    }

    public List<Guest> getAll() {
        return guests;
    }

    public Guest updateGuest(Guest request, String email) {
        var findGuest = getGuestByEmail(email);

        Guest guestToUpdate = findGuest.get();
        guestToUpdate.setFirstName(request.getFirstName());
        guestToUpdate.setLastName(request.getLastName());
        guestToUpdate.setPhoneNumber(request.getPhoneNumber());
        guestToUpdate.setEmailAddress(request.getEmailAddress());
        return guestToUpdate;
    }

    public Optional<Guest> deleteGuest(String email) {
        Optional<Guest> guestToDelete = getGuestByEmail(email);

        guestToDelete.ifPresentOrElse(guest -> {
                    guests.remove(guestToDelete.get());
                    System.out.println("Deleted guest: " + email);
                },
                () -> System.err.println("No guest found with name: " + email));
        return guestToDelete;
    }
}
