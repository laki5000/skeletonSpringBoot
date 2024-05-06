package com.example.utils.dto.response;

import lombok.Getter;

/**
 * Error response class.
 */
@Getter
public class ErrorResponseDTO extends BaseResponseDTO {
    private final Integer errorCode;

    /**
     * Constructor.
     *
     * @param errorCode the error code
     * @param message   the error message
     */
    public ErrorResponseDTO(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
