package com.example.user.dto.request;

import com.example.annotation.IsValidPassword;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/** DTO class for updating a user. */
@Getter
@Builder
@Jacksonized
public class UserUpdateRequestDTO {
    @IsValidPassword private String password;
}
