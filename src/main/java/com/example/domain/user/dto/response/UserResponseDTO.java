package com.example.domain.user.dto.response;

import java.time.Instant;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** DTO class for user response. */
@Getter
@Builder
@EqualsAndHashCode
public class UserResponseDTO {
    private Long id;
    private String username;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    private UserDetailsResponseDTO details;
}
