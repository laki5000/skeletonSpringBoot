package com.example.exception;

import static org.springframework.http.HttpStatus.*;

import com.example.utils.dto.response.ErrorResponseDTO;
import com.example.utils.service.IMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Controller advice class for handling exceptions globally. */
@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final IMessageService messageService;

    public static final String ERROR_DEFAULT_MESSAGE = "error.default_message";

    /**
     * Handles generic exceptions.
     *
     * @param ex the exception to handle
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        return handleException(ex, ex.getMessage(), INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles conflict exceptions.
     *
     * @param ex the exception to handle
     * @return the response entity
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictExceptions(Exception ex) {
        return handleException(ex, ex.getMessage(), CONFLICT);
    }

    /**
     * Handle not found exceptions.
     *
     * @param ex the exception to handle
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundExceptions(Exception ex) {
        return handleException(ex, ex.getMessage(), NOT_FOUND);
    }

    /**
     * Handle not modified exceptions.
     *
     * @param ex the exception to handle
     * @return the response entity
     */
    @ExceptionHandler(NotModifiedException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotModifiedExceptions(Exception ex) {
        return handleException(ex, ex.getMessage(), NOT_MODIFIED);
    }

    /**
     * Handle bad request exceptions.
     *
     * @param ex the exception to handle
     * @return the response entity
     */
    @ExceptionHandler({InvalidDateFormatException.class, InvalidFilterException.class})
    public ResponseEntity<ErrorResponseDTO> handleBadRequestExceptions(Exception ex) {
        return handleException(ex, ex.getMessage(), BAD_REQUEST);
    }

    /**
     * Handle validation exceptions.
     *
     * @param ex exception to handle
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();

        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> errorMessage.append(error.getDefaultMessage()).append(", "));

        return handleException(ex, errorMessage.toString(), BAD_REQUEST);
    }

    /**
     * Handle Exceptions.
     *
     * @param ex the exception to handle
     * @param errorMessage the error message to return
     * @param statusCode the status code to return
     * @return the response entity
     */
    private ResponseEntity<ErrorResponseDTO> handleException(
            Exception ex, String errorMessage, HttpStatus statusCode) {
        log.error("{} occurred: {}", ex.getClass().getSimpleName(), ex);

        ErrorResponseDTO errorResponse =
                ErrorResponseDTO.builder()
                        .errorCode(statusCode.value())
                        .message(
                                messageService.getMessage(ERROR_DEFAULT_MESSAGE)
                                        + " "
                                        + errorMessage)
                        .build();

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
