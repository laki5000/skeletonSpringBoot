package com.example.domain.user.dto.request;

import static com.example.constants.ValidationConstants.*;

import com.example.annotation.IsValidPassword;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** DTO class for user create request. */
@Getter
@Setter
@Builder
public class UserCreateRequestDTO {
    @NotNull(message = USERNAME_REQUIRED_MESSAGE)
    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = USERNAME_SIZE_MESSAGE)
    private String username;

    @IsValidPassword private String password;

    @NotNull(message = DETAILS_REQUIRED_MESSAGE)
    @Valid
    private UserDetailsRequestDTO details;
}
