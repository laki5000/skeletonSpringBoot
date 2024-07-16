package com.example.exception;

/** Exception thrown when a conflict occurs. */
public class ConflictException extends RuntimeException {
    /**
     * Constructs a new conflict exception with the specified detail message.
     *
     * @param message the detail message
     */
    public ConflictException(String message) {
        super(message);
    }
}
