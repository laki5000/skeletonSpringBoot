package com.example.user.annotation;

import com.example.user.validation.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation for password validation. */
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidPassword {
    String message() default "Invalid password";

    String nullMessage() default "Password is required";

    String minLengthMessage() default "Password must be at least 8 characters long";

    String maxLengthMessage() default "Password must be no more than 64 characters long";

    String patternMessage() default
            "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
