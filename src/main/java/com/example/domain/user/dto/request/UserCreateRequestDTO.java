package com.example.domain.user.dto.request;

import static com.example.utils.constants.ValidationConstants.*;

import com.example.annotation.IsValidPassword;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

/** DTO class for user creation. */
@Getter
@Builder
public class UserCreateRequestDTO {
    @NotNull(message = USERNAME_REQUIRED_MESSAGE)
    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = USERNAME_SIZE_MESSAGE)
    private String username;

    @IsValidPassword private String password;
}
