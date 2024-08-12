package com.example.user.dto.request;

import com.example.annotation.IsValidPassword;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

/** Dto class for creating a user. */
@Getter
@Builder
public class UserCreateRequestDTO {
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @IsValidPassword private String password;
}
