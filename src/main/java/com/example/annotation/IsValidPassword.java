package com.example.annotation;

import static com.example.constants.Constants.*;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.validation.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Annotation class for password validation. */
@Constraint(validatedBy = PasswordValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface IsValidPassword {
    String message() default INVALID_PASSWORD_MESSAGE;

    String minLengthMessage() default PASSWORD_MIN_LENGTH_MESSAGE;

    String maxLengthMessage() default PASSWORD_MAX_LENGTH_MESSAGE;

    String patternMessage() default PASSWORD_PATTERN_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
