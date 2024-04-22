package com.example.utils;

import lombok.Getter;

/**
 * Error response class.
 *
 * Suppresses all warnings including "Field can be converted to a local variable"
 * and "Private field 'errorCode' is assigned but never accessed" warnings.
 */
@SuppressWarnings("all")
@Getter
public class ErrorResponse extends BaseResponse {
    private final Integer errorCode;

    /**
     * Constructor.
     *
     * @param errorCode the error code
     * @param message   the error message
     */
    public ErrorResponse(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
