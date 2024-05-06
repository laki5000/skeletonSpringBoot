package com.example.utils.controller;

import com.example.exception.ConflictException;
import com.example.exception.InvalidDateFormatException;
import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
import com.example.utils.dto.response.ErrorResponseDTO;
import com.example.utils.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return handleException(ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle ConflictException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleMyConflictException(Exception ex) {
        return handleException(ex, ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handle NotFoundException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleMyNotFoundException(Exception ex) {
        return handleException(ex, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle NotModifiedException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(NotModifiedException.class)
    public ResponseEntity<?> handleMyNotModifiedException(Exception ex) {
        return handleException(ex, ex.getMessage(), HttpStatus.NOT_MODIFIED);
    }

    /**
     * Handle InvalidDateFormatException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<?> handleMyInvalidDateFormatException(Exception ex) {
        return handleException(ex, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle validation exceptions.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorMessage.append(error.getDefaultMessage()).append(", ");
        });

        return handleException(ex, errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle exceptions.
     *
     * @param ex         the exception
     * @param statusCode the status code
     * @return the response entity
     */
    public ResponseEntity<?> handleException(Exception ex, String errorMessage, HttpStatus statusCode) {
        log.error("Handling exception", ex);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(statusCode.value(), messageService.getMessage("error.default_message") + " " + errorMessage);

        log.error("Returning error response: {}", errorResponse);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
