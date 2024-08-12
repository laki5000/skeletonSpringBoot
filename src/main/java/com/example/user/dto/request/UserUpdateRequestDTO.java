package com.example.user.dto.request;

import com.example.annotation.IsValidPassword;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/** DTO class for updating a user. */
@Getter
@Builder
public class UserUpdateRequestDTO {
    @NotNull(message = "Id is required")
    private Long id;

    @IsValidPassword private String password;
}
