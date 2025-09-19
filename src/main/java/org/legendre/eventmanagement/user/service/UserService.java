package org.legendre.eventmanagement.user.service;

import org.legendre.eventmanagement.user.model.User;
import org.legendre.eventmanagement.user.model.requests.ChangePasswordRequest;
import org.legendre.eventmanagement.user.model.requests.SignUpRequest;
import org.legendre.eventmanagement.user.model.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse signUp(SignUpRequest request);

    UserResponse getUserByUsername(String username);

    List<UserResponse> getAll();

    void changePassword(ChangePasswordRequest request);

    void deleteUser(String username);
}
