package com.example.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Dto class for update a user. */
@Getter
@AllArgsConstructor
public class UserUpdateRequest {
    private Long id;
    private String password;
}
