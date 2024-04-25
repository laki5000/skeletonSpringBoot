package com.example.user.dto.request;

import lombok.Getter;

/** Dto class for creating a user. */
@Getter
public class UserCreateRequest {
    private String username;
    private String password;
}
