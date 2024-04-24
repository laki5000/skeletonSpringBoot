package com.example.exception;

/**
 * Exception thrown when a resource is not found.
 */
public class MyNotFoundException extends RuntimeException {
    /**
     * Constructs a new not found exception with the specified detail message.
     *
     * @param message the detail message
     */
    public MyNotFoundException(String message) {
        super(message);
    }
}
