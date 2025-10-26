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

import java.util.List;
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

        @Test
        void testUpdateGuest_success() {
            // Given
            var existingEmail = "Mariamj@gmail.com";
            var request = new GuestRequest("Dammy", "Ayo", "08012345678", "Dammyayo@gmail.com");
            var existingGuest = Guest.builder()
                    .firstName("Mariam")
                    .lastName("J")
                    .emailAddress(existingEmail)
                    .phoneNumber("09011112222")
                    .build();

            // When-Then
            when(guestRepository.findByEmailAddress(existingEmail))
                    .thenReturn(Optional.of(existingGuest));
            when(guestRepository.findByEmailAddress(request.emailAddress()))
                    .thenReturn(Optional.empty());
            when(guestRepository.save(any(Guest.class)))
                    .thenReturn(existingGuest.toBuilder()
                            .firstName(request.firstName())
                            .lastName(request.lastName())
                            .emailAddress(request.emailAddress())
                            .phoneNumber(request.phoneNumber())
                            .build());

            var response = guestService.updateGuest(request, existingEmail);

            // Verify / Assert
            assertEquals(request.firstName(), response.getFirstName());
            assertEquals(request.lastName(), response.getLastName());
            assertEquals(request.emailAddress(), response.getEmailAddress());
            assertEquals(request.phoneNumber(), response.getPhoneNumber());
            verify(guestRepository, times(1)).save(any(Guest.class));
        }

        @Test
        void testUpdateGuest_throwDuplicateRecordIfNewEmailAlreadyExists() {
            // Given
            var existingEmail = "Mariamj@gmail.com.com";
            var request = new GuestRequest("Dammy", "Ayo", "08012345678", "Dammyayo@gmail.com");
            var existingGuest = Guest.builder()
                    .firstName("Mariam")
                    .lastName("J")
                    .emailAddress(existingEmail)
                    .phoneNumber("09011112222")
                    .build();
            var duplicateGuest = Guest.builder()
                    .firstName("Jane")
                    .lastName("Smith")
                    .emailAddress(request.emailAddress())
                    .phoneNumber("08123456789")
                    .build();

            // When-Then
            when(guestRepository.findByEmailAddress(existingEmail))
                    .thenReturn(Optional.of(existingGuest));
            when(guestRepository.findByEmailAddress(request.emailAddress()))
                    .thenReturn(Optional.of(duplicateGuest));

            var exception = assertThrows(DuplicateRecordException.class,
                    () -> guestService.updateGuest(request, existingEmail));

            // Verify / Assert
            assertEquals("Guest already exist", exception.getErrorResponse().getMessage());
            assertEquals("02", exception.getErrorResponse().getCode());
            verify(guestRepository, times(0)).save(any(Guest.class));
        }
    }

    @Nested
    class getGuest {

        @Test
        void testGetGuestByEmail_success() {
            // Given
            var email = "Gbemi@gmail.com";
            var guest = Guest.builder()
                    .firstName("Gbemi")
                    .lastName("Akin")
                    .emailAddress(email)
                    .phoneNumber("08011112222")
                    .build();

            // When-Then
            when(guestRepository.findByEmailAddress(email))
                    .thenReturn(Optional.of(guest));

            var result = guestService.getGuestByEmail(email);

            // Verify / Assert
            assertEquals(guest.getEmailAddress(), result.get().getEmailAddress());
            assertEquals(guest.getFirstName(), result.get().getFirstName());
            verify(guestRepository, times(1)).findByEmailAddress(email);
        }

        @Test
        void testGetAllGuests_success() {
            // Given
            var guestList = List.of(
                    Guest.builder().firstName("Dammy").lastName("Ayo").emailAddress("Dammyayo@gmail.com").build(),
                    Guest.builder().firstName("Mariam").lastName("J").emailAddress("Mariamj@gmail.com").build()
            );

            // When-Then
            when(guestRepository.findAll()).thenReturn(guestList);

            var result = guestService.getAll();

            // Verify / Assert
            assertEquals(2, result.size());
            verify(guestRepository, times(1)).findAll();
        }
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