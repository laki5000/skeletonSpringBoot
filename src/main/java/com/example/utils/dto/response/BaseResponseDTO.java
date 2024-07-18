package com.example.utils.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** DTO class for the base response. */
@Getter
@SuperBuilder
public class BaseResponseDTO {
    private final String message;
}
