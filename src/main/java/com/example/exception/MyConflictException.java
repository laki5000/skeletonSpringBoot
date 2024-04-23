package com.example.exception;

public class MyConflictException extends RuntimeException {
    public MyConflictException(String message) {
        super(message);
    }
}
