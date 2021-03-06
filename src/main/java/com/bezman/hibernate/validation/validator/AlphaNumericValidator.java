package com.bezman.hibernate.validation.validator;

import com.bezman.hibernate.validation.annotation.AlphaNumeric;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlphaNumericValidator implements ConstraintValidator<AlphaNumeric, String> {
    private boolean spaces;

    @Override
    public void initialize(AlphaNumeric constraintAnnotation) {
        this.spaces = constraintAnnotation.spaces();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!spaces) {
            return value.matches("^[a-zA-Z0-9_]*$");
        }

        System.out.println(value);

        return value.matches("^[\\w ]+$");
    }
}
