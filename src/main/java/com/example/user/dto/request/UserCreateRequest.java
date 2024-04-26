package com.example.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Dto class for creating a user. */
@Getter
@AllArgsConstructor
public class UserCreateRequest {
    private String username;
    private String password;
}
