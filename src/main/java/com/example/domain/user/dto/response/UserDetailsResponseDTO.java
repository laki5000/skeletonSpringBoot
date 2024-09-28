package com.example.domain.user.dto.response;

import java.time.Instant;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** DTO class for user details response. */
@Getter
@Builder
@EqualsAndHashCode
public class UserDetailsResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
