package com.example.user.dto.response;

import java.util.Date;

/**
 * Dto class for getting a user.
 */
public record UserGetResponse(Long id, String username, Date createdAt, Date updatedAt, String createdBy, String updatedBy) {
}

