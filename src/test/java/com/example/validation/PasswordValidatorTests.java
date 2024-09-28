package com.example.validation;

import static com.example.Constants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordValidatorTests {

    @InjectMocks private PasswordValidator passwordValidator;
    @Mock private ConstraintValidatorContext context;
    @Mock private ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;

    @Test
    @DisplayName("Tests the successful validation of a password.")
    void isValid_Success() {
        // Given When & Then
        assertTrue(passwordValidator.isValid(TEST_PASSWORD, context));
    }

    @Test
    @DisplayName("Tests the unsuccessful validation of a null password due to a null value.")
    void isValid_NullPassword() {
        // Given When & Then
        assertTrue(passwordValidator.isValid(null, context));
    }

    @Test
    @DisplayName("Tests the unsuccessful validation of a password that is too short.")
    void isValid_TooShortPassword() {
        // Given
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);
        when(violationBuilder.addConstraintViolation()).thenReturn(context);

        // When & Then
        assertFalse(passwordValidator.isValid(TEST_INVALID_SHORT_PASSWORD, context));

        verify(context).buildConstraintViolationWithTemplate(any());
        verify(violationBuilder).addConstraintViolation();
    }

    @Test
    @DisplayName("Tests the unsuccessful validation of a password that is too long.")
    void isValid_TooLongPassword() {
        // Given
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);
        when(violationBuilder.addConstraintViolation()).thenReturn(context);

        // When & Then
        assertFalse(passwordValidator.isValid(TEST_INVALID_LONG_PASSWORD, context));

        verify(context).buildConstraintViolationWithTemplate(any());
        verify(violationBuilder).addConstraintViolation();
    }

    @Test
    @DisplayName("Tests the unsuccessful validation of a password that does not match the regex.")
    void isValid_NotMatchingRegex() {
        // Given
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(violationBuilder);
        when(violationBuilder.addConstraintViolation()).thenReturn(context);

        // When & Then
        assertFalse(passwordValidator.isValid(TEST_INVALID_PASSWORD, context));

        verify(context).buildConstraintViolationWithTemplate(any());
        verify(violationBuilder).addConstraintViolation();
    }
}
