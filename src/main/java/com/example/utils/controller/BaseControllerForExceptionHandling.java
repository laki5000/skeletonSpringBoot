package com.example.utils.controller;

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
     * Handles generic exceptions.
     *
     * @param ex the exception
     * @return the error response
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleGenericException(Exception ex) {
        log.error("Handling generic exception", ex);

        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(statusCode.value(), messageService.getMessage("base.controller.exception.response01") + ex.getMessage());

        log.error("Returning error response: {}", errorResponse);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
