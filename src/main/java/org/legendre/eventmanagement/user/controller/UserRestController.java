package org.legendre.eventmanagement.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.legendre.eventmanagement.user.model.User;
import org.legendre.eventmanagement.user.model.requests.ChangePasswordRequest;
import org.legendre.eventmanagement.user.model.requests.SignUpRequest;
import org.legendre.eventmanagement.user.model.response.UserResponse;
import org.legendre.eventmanagement.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.api.APIs.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_URL)
public class UserRestController {

    private final UserService userService;

    @PostMapping(SIGNUP_PATH)
    private ResponseEntity<?> createUser(@RequestBody @Valid SignUpRequest request) {
        return new ResponseEntity<>(userService.signUp(request), HttpStatus.CREATED);
    }

    @GetMapping(GET_PATH)
    private ResponseEntity<?> getUser(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/get-users")
    private ResponseEntity<List<UserResponse>> getUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PutMapping(CHANGE_PASSWORD_PATH)
    private ResponseEntity<Void> updateGuest(@RequestBody @Valid ChangePasswordRequest request) {
        userService.changePassword(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(DELETE_PATH)
    private ResponseEntity<Void> deleteGuest(@PathVariable(GET_BY_NAME_PATH_VARIABLE) String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
