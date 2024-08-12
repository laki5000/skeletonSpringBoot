package com.example.exception;

import com.example.utils.dto.response.ErrorResponseDTO;
import com.example.utils.service.IMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Controller class for handling exceptions globally. */
@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_DEFAULT_MESSAGE = "error.default_message";

    private final IMessageService messageService;

    /**
     * Handle generic exception.
     *
     * @param ex the Exception to handle
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        return handleException(ex, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle ConflictException.
     *
     * @param ex the ConflictException to handle
     * @return the response entity
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleMyConflictException(Exception ex) {
        return handleException(ex, ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handle NotFoundException.
     *
     * @param ex the NotFoundException to handle
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleMyNotFoundException(Exception ex) {
        return handleException(ex, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle NotModifiedException.
     *
     * @param ex the NotModifiedException to handle
     * @return the response entity
     */
    @ExceptionHandler(NotModifiedException.class)
    public ResponseEntity<ErrorResponseDTO> handleMyNotModifiedException(Exception ex) {
        return handleException(ex, ex.getMessage(), HttpStatus.NOT_MODIFIED);
    }

    /**
     * Handle InvalidDateFormatException.
     *
     * @param ex the InvalidDateFormatException to handle
     * @return the response entity
     */
    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleMyInvalidDateFormatException(Exception ex) {
        return handleException(ex, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle MethodArgumentNotValidException.
     *
     * @param ex MethodArgumentNotValidException to handle
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();

        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> errorMessage.append(error.getDefaultMessage()).append(", "));

        return handleException(ex, errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle Exceptions.
     *
     * @param ex the Exception to handle
     * @param statusCode the status code to return
     * @return the response entity
     */
    public ResponseEntity<ErrorResponseDTO> handleException(
            Exception ex, String errorMessage, HttpStatus statusCode) {
        log.error("Handling exception", ex);

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
