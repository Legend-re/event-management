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

    public Optional<Guest> getGuestByName(String name) {
        var foundGuest = guests.stream().filter(guest -> guest.getFirstName().equalsIgnoreCase(name))
                .findFirst();
        foundGuest.ifPresentOrElse((
                        guest -> System.out.println("Found guest: " + name)),
                () -> System.err.println("No guest found with name: " + name));

        return foundGuest;
    }

    public List<Guest> getAll() {
        return guests;
    }

    public Guest updateGuest(Guest request, String name) {
        var findGuest = getGuestByName(name);

        Guest guestToUpdate = findGuest.get();
        guestToUpdate.setFirstName(request.getFirstName());
        guestToUpdate.setLastName(request.getLastName());
        guestToUpdate.setPhoneNumber(request.getPhoneNumber());
        guestToUpdate.setEmailAddress(request.getEmailAddress());
        return guestToUpdate;
    }

    public Optional<Guest> deleteGuest(String name) {
        Optional<Guest> guestToDelete = getGuestByName(name);

        guestToDelete.ifPresentOrElse(guest -> {
                    guests.remove(guestToDelete.get());
                    System.out.println("Deleted guest: " + name);
                },
                () -> System.err.println("No guest found with name: " + name));
        return guestToDelete;
    }
}
