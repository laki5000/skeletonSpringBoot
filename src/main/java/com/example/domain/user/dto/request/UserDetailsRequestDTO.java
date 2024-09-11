package com.example.domain.user.dto.request;

import static com.example.utils.constants.ValidationConstants.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

/** DTO class for user details request. */
@Getter
@Builder
public class UserDetailsRequestDTO {
    @NotBlank(message = FIRST_NAME_REQUIRED_MESSAGE)
    @Size(
            min = FIRST_NAME_MIN_LENGTH,
            max = FIRST_NAME_MAX_LENGTH,
            message = FIRST_NAME_SIZE_MESSAGE)
    private String firstName;

    @NotBlank(message = LAST_NAME_REQUIRED_MESSAGE)
    @Size(min = LAST_NAME_MIN_LENGTH, max = LAST_NAME_MAX_LENGTH, message = LAST_NAME_SIZE_MESSAGE)
    private String lastName;
}
