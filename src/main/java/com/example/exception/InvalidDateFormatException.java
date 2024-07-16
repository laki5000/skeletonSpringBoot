package com.example.exception;

/** Exception thrown when a date is invalid. */
public class InvalidDateFormatException extends RuntimeException {
  /**
   * Constructs a new invalid date format exception with the specified detail message.
   *
   * @param message the detail message
   */
  public InvalidDateFormatException(String message) {
    super(message);
  }
}
