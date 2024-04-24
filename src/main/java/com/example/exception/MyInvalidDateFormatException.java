package com.example.exception;

/**
 * Exception thrown when a date is invalid.
 */
public class MyInvalidDateFormatException extends RuntimeException {
    /**
     * Constructs a new invalid date format exception with the specified detail message.
     *
     * @param message the detail message
     */
    public MyInvalidDateFormatException(String message) {
        super(message);
    }
}
