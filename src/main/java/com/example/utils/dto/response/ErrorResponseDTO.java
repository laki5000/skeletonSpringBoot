package com.example.utils.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** DTO class for the error response. */
@Getter
@SuperBuilder
@NoArgsConstructor
public class ErrorResponseDTO extends BaseResponseDTO {
    private Integer errorCode;
}
