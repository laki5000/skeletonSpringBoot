package com.example.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Base controller for exception handling. */
public abstract class BaseControllerForExceptionHandling {
    /**
     * Handles generic exceptions.
     *
     * @param ex the exception
     * @return the error response
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleGenericException(Exception ex) {
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(statusCode.value(), "Internal Server Error: " + ex.getMessage());

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
