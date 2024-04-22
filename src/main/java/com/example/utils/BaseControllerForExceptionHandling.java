package com.example.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Base controller for exception handling. */
@Getter
public abstract class BaseControllerForExceptionHandling {
    private final LoggerService loggerService;

    /**
     * Constructor.
     *
     * @param loggerService the logger service
     */
    public BaseControllerForExceptionHandling(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    /**
     * Handles generic exceptions.
     *
     * @param ex the exception
     * @return the error response
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleGenericException(Exception ex) {
        loggerService.logError(BaseControllerForExceptionHandling.class, ex.getMessage());

        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(statusCode.value(), "Internal Server Error: " + ex.getMessage());

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
