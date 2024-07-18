package com.example.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** Dto class for creating a user. */
@Getter
@SuperBuilder
public class UserCreateRequestDTO extends OnlyPasswordDTO {
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private final String username;
}
