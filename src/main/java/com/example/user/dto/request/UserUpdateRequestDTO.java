package com.example.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** DTO class for updating a user. */
@Getter
@SuperBuilder
@NoArgsConstructor
public class UserUpdateRequestDTO extends OnlyPasswordDTO {
    @NotNull(message = "Id is required")
    private Long id;
}
