package com.example.utils.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** DTO class for the success response. */
@Getter
@SuperBuilder
@NoArgsConstructor
public class SuccessResponseDTO extends BaseResponseDTO {
    private Object data;
}
