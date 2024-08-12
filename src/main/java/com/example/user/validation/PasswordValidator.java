package com.example.user.validation;

import com.example.user.annotation.IsValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/** Validator class for password validation. */
public class PasswordValidator implements ConstraintValidator<IsValidPassword, String> {

    private String nullMessage;
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
        this.nullMessage = constraintAnnotation.nullMessage();
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
            context.buildConstraintViolationWithTemplate(nullMessage)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            return false;
        }

        if (value.length() < 8) {
            context.buildConstraintViolationWithTemplate(minLengthMessage)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            return false;
        }

        if (value.length() > 64) {
            context.buildConstraintViolationWithTemplate(maxLengthMessage)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            return false;
        }

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (!value.matches(regex)) {
            context.buildConstraintViolationWithTemplate(patternMessage)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            return false;
        }

        return true;
    }
}
