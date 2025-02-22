package org.example.validate.validator;

import org.example.validate.MinLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinLengthValidator implements ConstraintValidator<MinLength, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.length() > 2;
    }
}
