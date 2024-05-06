package com.example.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** Dto class for update a user. */
@Getter
@AllArgsConstructor
public class UserUpdateRequestDTO {
    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must be between 3 and 64 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;
}
