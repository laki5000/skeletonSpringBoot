package com.example.user.dto.request;

import lombok.Getter;

/** Dto class for update a user. */
@Getter
public class UserUpdateRequest {
    private Long id;
    private String password;
}
