package com.example.utils.controller;

import com.example.exception.MyConflictException;
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

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleGenericException(Exception ex) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MyConflictException.class)
    protected ResponseEntity<?> handleMyConflictException(Exception ex) {
        return handleException(ex, HttpStatus.CONFLICT);
    }

    protected ResponseEntity<?> handleException(Exception ex, HttpStatus statusCode) {
        log.error("Handling exception", ex);

        ErrorResponse errorResponse = new ErrorResponse(statusCode.value(), messageService.getMessage("exception.error") + ex.getMessage());

        log.error("Returning error response: {}", errorResponse);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
