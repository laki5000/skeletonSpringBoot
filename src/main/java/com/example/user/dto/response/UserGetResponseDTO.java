package com.example.user.dto.response;

import lombok.Getter;

import java.time.Instant;

/**
 * DTO class for returning user data.
 */
@Getter
public class UserGetResponseDTO {
    private Long id;
    private String username;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}