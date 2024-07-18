package com.example.user.dto.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

/** DTO class for returning user data. */
@Getter
@Builder
public class UserGetResponseDTO {
    private final Long id;
    private final String username;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final String createdBy;
    private final String updatedBy;
}
