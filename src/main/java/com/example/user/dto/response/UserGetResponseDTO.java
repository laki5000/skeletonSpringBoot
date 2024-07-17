package com.example.user.dto.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** DTO class for returning user data. */
@Getter
@Setter
@Builder
public class UserGetResponseDTO {
    private Long id;
    private String username;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
