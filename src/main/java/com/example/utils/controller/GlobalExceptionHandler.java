package com.example.utils.controller;

import com.example.exception.ConflictException;
import com.example.exception.InvalidDateFormatException;
import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
import com.example.utils.dto.response.ErrorResponse;
import com.example.utils.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Base controller for exception handling. */
@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {
    private final MessageService messageService;

    /**
     * Constructor.
     *
     * @param messageService the message service
     */
    public GlobalExceptionHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Handle generic exception.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleGenericException(Exception ex) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle ConflictException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<?> handleMyConflictException(Exception ex) {
        return handleException(ex, HttpStatus.CONFLICT);
    }

    /**
     * Handle NotFoundException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleMyNotFoundException(Exception ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle NotModifiedException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(NotModifiedException.class)
    protected ResponseEntity<?> handleMyNotModifiedException(Exception ex) {
        return handleException(ex, HttpStatus.NOT_MODIFIED);
    }

    /**
     * Handle InvalidDateFormatException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(InvalidDateFormatException.class)
    protected ResponseEntity<?> handleMyInvalidDateFormatException(Exception ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle exceptions.
     *
     * @param ex         the exception
     * @param statusCode the status code
     * @return the response entity
     */
    protected ResponseEntity<?> handleException(Exception ex, HttpStatus statusCode) {
        log.error("Handling exception", ex);

        ErrorResponse errorResponse = new ErrorResponse(statusCode.value(), messageService.getMessage("error.default_message") + " " + ex.getMessage());

        log.error("Returning error response: {}", errorResponse);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
