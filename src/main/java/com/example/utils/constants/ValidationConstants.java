package com.example.utils.constants;

/** Constants used in the application validation. */
public class ValidationConstants {
    public static final String INVALID_PASSWORD_MESSAGE = "Invalid password";
    public static final String PASSWORD_REQUIRED_MESSAGE = "Password is required";
    public static final String PASSWORD_MIN_LENGTH_MESSAGE =
            "Password must be at least 8 characters long";
    public static final String PASSWORD_MAX_LENGTH_MESSAGE =
            "Password must be no more than 64 characters long";
    public static final String PASSWORD_PATTERN_MESSAGE =
            "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character";
    public static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,64}$";
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 64;

    public static final String USERNAME_REQUIRED_MESSAGE = "Username is required";
    public static final String USERNAME_SIZE_MESSAGE =
            "Username must be between 3 and 20 characters";
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 20;
}