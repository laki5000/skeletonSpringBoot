package com.example.user.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** DTO class for containing only a password. */
@Getter
@SuperBuilder
public class OnlyPasswordDTO {
    @NotNull(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must be between 3 and 64 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message =
                    "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private final String password;

    @JsonCreator
    public OnlyPasswordDTO(@JsonProperty("password") String password) {
        this.password = password;
    }
}
