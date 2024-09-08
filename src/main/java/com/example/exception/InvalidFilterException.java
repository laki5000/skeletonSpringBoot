package com.example.exception;

/** Exception thrown when a filter is invalid. */
public class InvalidFilterException extends RuntimeException {
    /**
     * Constructs a new invalid filter exception with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidFilterException(String message) {
        super(message);
    }
}
