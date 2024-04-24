package com.example.utils.controller;

import com.example.exception.MyConflictException;
import com.example.exception.MyNotFoundException;
import com.example.exception.MyNotModifiedException;
import com.example.utils.dto.response.ErrorResponse;
import com.example.utils.service.MessageService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Base controller for exception handling. */
@Log4j2
@Getter
public abstract class BaseControllerForExceptionHandling {
    private final MessageService messageService;

    /**
     * Constructor.
     *
     * @param messageService the message service
     */
    public BaseControllerForExceptionHandling(MessageService messageService) {
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
     * Handle MyConflictException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(MyConflictException.class)
    protected ResponseEntity<?> handleMyConflictException(Exception ex) {
        return handleException(ex, HttpStatus.CONFLICT);
    }

    /**
     * Handle MyNotFoundException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(MyNotFoundException.class)
    protected ResponseEntity<?> handleMyNotFoundException(Exception ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle MyNotModifiedException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(MyNotModifiedException.class)
    protected ResponseEntity<?> handleMyNotModifiedException(Exception ex) {
        return handleException(ex, HttpStatus.NOT_MODIFIED);
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

        ErrorResponse errorResponse = new ErrorResponse(statusCode.value(), messageService.getMessage("exception.error") + ex.getMessage());

        log.error("Returning error response: {}", errorResponse);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
