package com.example.exception;

/**
 * Exception thrown when a resource is not modified.
 */
public class MyNotModifiedException extends RuntimeException {
    /**
     * Constructs a new not modified exception with the specified detail message.
     *
     * @param message the detail message
     */
    public MyNotModifiedException(String message) {
        super(message);
    }
}
