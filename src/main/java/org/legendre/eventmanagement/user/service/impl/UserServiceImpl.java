package org.legendre.eventmanagement.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legendre.eventmanagement.exception.*;
import org.legendre.eventmanagement.security.JWTService;
import org.legendre.eventmanagement.user.model.User;
import org.legendre.eventmanagement.user.model.repository.UserRepository;
import org.legendre.eventmanagement.user.model.requests.ChangePasswordRequest;
import org.legendre.eventmanagement.user.model.requests.LoginRequest;
import org.legendre.eventmanagement.user.model.requests.SignUpRequest;
import org.legendre.eventmanagement.user.model.response.LoginResponse;
import org.legendre.eventmanagement.user.model.response.UserResponse;
import org.legendre.eventmanagement.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.legendre.eventmanagement.exception.ErrorMessages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public UserResponse signUp(SignUpRequest request) {
        log.info("signing up new user: {}", request.email());
        userRepository.findByUsername(request.email())
                .ifPresent(host -> {
                    log.error("User already exist: {}", request.email());
                    throw new DuplicateRecordException(
                            new ErrorResponse(USER_ALREADY_EXIST.getMessage(), ErrorCode.RSC02)
                    );
                });

//        if(request.password().equals(request.confirmPassword())){
//             password = passwordEncoder.encode(request.password());
//        } else {
//            throw new RuntimeException("Passwords do not match");
//        }

        String password = Optional.of(request)
                .filter(confirmPassword -> request.password().equals(request.confirmPassword()))
                .map(encodePassword -> passwordEncoder.encode(request.password()))
                .orElseThrow(() -> new ValidationException(
                        new ErrorResponse(PASSWORD_MISMATCH.getMessage(), ErrorCode.RSC01))
                );

        var savedUser = userRepository.save(
                User.builder()
                        .username(request.email())
                        .password(password)
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .role("USER")
                        .build());
        log.info("user created successfully: {}", savedUser.getUsername());
        return new UserResponse(savedUser.getUsername(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getRole());
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        log.info("Fetching user by email: {}", username);
        var getUser = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> {
                            log.error("User not found: {}", username);
                            return new RecordNotFoundException(
                                    new ErrorResponse(USER_NOT_FOUND.getMessage(), ErrorCode.RSC01));
                        }
                ));
        return getUser.map(User::toResponse).get();
    }

    @Override
    public List<UserResponse> getAll() {
        log.info("Fetching all users");
        var users = userRepository.findAll();
        return users.stream().map(User::toResponse).toList();
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        log.info("updating a user's : {} password", request.username());
        var findUser = userRepository.findByUsername(request.username())
                .orElseThrow(() -> {
                            log.error("User not found: {}", request.username());
                            return new RecordNotFoundException(
                                    new ErrorResponse(HOST_NOT_FOUND.getMessage(), ErrorCode.RSC01));
                        }
                );

        Optional.of(request)
                .filter(
                        confirmOldPasswordAgainstExistingPassword -> passwordEncoder.matches(request.oldPassword(), findUser.getPassword()))
                .orElseThrow(() -> new ValidationException(
                        new ErrorResponse(PASSWORD_MISMATCH.getMessage().concat(": Your old Password is incorrect"), ErrorCode.RSC01))
                );

        String password = Optional.of(request)
                .filter(confirmPassword -> request.newPassword().equals(request.confirmPassword()))
                .map(encodePassword -> passwordEncoder.encode(request.newPassword()))
                .orElseThrow(() -> new ValidationException(
                        new ErrorResponse(PASSWORD_MISMATCH.getMessage().concat(": Your new password and confirm password is not the same"), ErrorCode.RSC01))
                );

        Optional.of(request)
                .filter(confirmNewPasswordIsNotOldPassword -> !passwordEncoder.matches(request.oldPassword(), password))
                .orElseThrow(() -> new ValidationException(
                        new ErrorResponse("Old Password cannot be the same as new password", ErrorCode.RSC01)));

        User savedUser = userRepository.save(
                findUser.toBuilder()
                        .password(password).build());
        log.info("User Password successfully changed : {}", savedUser.getUsername());
    }

    @Override
    public void deleteUser(String username) {
        log.info("Deleting user: {}", username);
        userRepository.findByUsername(username).ifPresent(userRepository::delete);
        log.info("User deleted successfully: {}", username);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String username = getUserByUsername(request.username()).username();

        Optional.of(request)
                .filter(authenticate -> authentication.isAuthenticated())
                .orElseThrow(() -> new ValidationException(
                        new ErrorResponse("Your password is incorrect ", ErrorCode.RSC01)));

        String token = jwtService.generateToken(request.username());
        return new LoginResponse(username, token, jwtService.extractExpiration(token));
    }
}