package org.legendre.eventmanagement.guest.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.legendre.eventmanagement.exception.DuplicateRecordException;
import org.legendre.eventmanagement.guest.model.Guest;
import org.legendre.eventmanagement.guest.model.GuestRequest;
import org.legendre.eventmanagement.guest.model.repository.GuestRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestServiceImplTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestServiceImpl guestService;

    @BeforeEach
    void setUp() {
        guestService = new GuestServiceImpl(guestRepository);
    }

    @Nested
    class createGuest {

        @Test
        void testCreateGuest_success() {
            //Given
            var request = createGuestRequest();

            //when-then
            when(guestRepository.findByEmailAddress(request.emailAddress()))
                    .thenReturn(Optional.empty());
            when(guestRepository.save(any(Guest.class)))
                    .thenReturn(saveGuest(request));

            var response = guestService.createGuest(request);

            //verify/assert
            assertEquals(response.getFirstName(), request.firstName());
            assertEquals(response.getLastName(), request.lastName());
            assertEquals(response.getPhoneNumber(), request.phoneNumber());
            assertEquals(response.getEmailAddress(), request.emailAddress());
            verify(guestRepository, times(1)).save(any(Guest.class));
        }

        @Test
        void testCreateGuest_throwDuplicateRecordExceptionIfGuestAlreadyExist() {
            //Given
            var request = createGuestRequest();

            //when-then
            when(guestRepository.findByEmailAddress(request.emailAddress()))
                    .thenReturn(Optional.of(saveGuest(request)));

            var exception = assertThrows(DuplicateRecordException.class,
                    () -> guestService.createGuest(request));

            //verify/assert
            assertEquals("Guest already exist", exception.getErrorResponse().getMessage());
            assertEquals("02", exception.getErrorResponse().getCode());
            verify(guestRepository, times(0)).save(any(Guest.class));
        }

        private static GuestRequest createGuestRequest() {
            return new GuestRequest("Mubarak", "Ajia",
                    "09019206373", "ajiamubarak3@gmail.com");
        }
    }

    @Nested
    class updateGuest {
        //for update guest 3 cases
        //success
        //if search email does not exist(String)
        //if email you're changing to already exists(email address within request)
    }

    @Nested
    class getGuest {
        //for get guests(get and get all) 3 cases
        // get single success
        // if email to be gotten does not exist
        // get all
    }

    @Nested
    class deleteGuest {

        @Test
        void testDeleteGuest_success() {
            //Given
            var email = "ajiamubarak3@gmail.com";

            //when-then
            when(guestRepository.findByEmailAddress(email))
                    .thenReturn(Optional.ofNullable(Guest.builder().emailAddress(email).build()));
            guestService.deleteGuest(email);

            //verify-assert
            verify(guestRepository, times(1)).delete(any(Guest.class));
        }
    }

    private static Guest saveGuest(GuestRequest request) {
        return Guest.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .emailAddress(request.emailAddress())
                .phoneNumber(request.phoneNumber()).build();
    }
}