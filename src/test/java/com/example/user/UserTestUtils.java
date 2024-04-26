package com.example.user;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserGetResponse;
import com.example.user.model.User;

import java.time.Instant;

public class UserTestUtils {
    public static UserCreateRequest getUserCreateRequest(String username, String password) {
        return new UserCreateRequest(username, password);
    }

    public static UserUpdateRequest getUserUpdateRequest(Long id, String password) {
        return new UserUpdateRequest(id, password);
    }

    public static User getUser(Long id, String username, String password, Instant createdAt, String createdBy, Instant updatedAt, String updatedBy) {
        return new User(id, username, password, createdAt, createdBy, updatedAt, updatedBy);
    }

    public static UserGetResponse getUserGetResponse(Long id, String username, Instant createdAt, Instant updatedAt, String createdBy, String updatedBy) {
        return new UserGetResponse(id, username, createdAt, updatedAt, createdBy, updatedBy);
    }
}
