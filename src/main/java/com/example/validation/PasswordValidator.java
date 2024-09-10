package com.example.validation;

import static com.example.utils.constants.ValidationConstants.*;

import com.example.annotation.IsValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/** Validator class for password validation. */
public class PasswordValidator implements ConstraintValidator<IsValidPassword, String> {

    private String minLengthMessage;
    private String maxLengthMessage;
    private String patternMessage;

    /**
     * Initializes the validator.
     *
     * @param constraintAnnotation the annotation instance
     */
    @Override
    public void initialize(IsValidPassword constraintAnnotation) {
        this.minLengthMessage = constraintAnnotation.minLengthMessage();
        this.maxLengthMessage = constraintAnnotation.maxLengthMessage();
        this.patternMessage = constraintAnnotation.patternMessage();
    }

    /**
     * Validates the password.
     *
     * @param value the password to validate
     * @param context the context in which the constraint is evaluated
     * @return true if the password is valid, false otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.length() < PASSWORD_MIN_LENGTH) {
            context.buildConstraintViolationWithTemplate(minLengthMessage)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            return false;
        }

        if (value.length() > PASSWORD_MAX_LENGTH) {
            context.buildConstraintViolationWithTemplate(maxLengthMessage)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            return false;
        }

        if (!value.matches(PASSWORD_REGEX)) {
            context.buildConstraintViolationWithTemplate(patternMessage)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            return false;
        }

        return true;
    }
}
