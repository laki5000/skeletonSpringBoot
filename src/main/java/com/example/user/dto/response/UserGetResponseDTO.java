package com.example.user.dto.response;

import java.time.Instant;

/**
 * Dto class for getting a user.
 */
public record UserGetResponseDTO(Long id, String username, Instant createdAt, Instant updatedAt, String createdBy,
                                 String updatedBy) {
}
