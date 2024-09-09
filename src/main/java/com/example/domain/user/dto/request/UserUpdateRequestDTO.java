package com.example.domain.user.dto.request;

import com.example.annotation.IsValidPassword;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/** DTO class for user update. */
@Getter
@Builder
@Jacksonized
public class UserUpdateRequestDTO {
    @IsValidPassword private String password;
}
