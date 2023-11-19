package org.example.validate;

import org.example.validate.validator.NotNegativeValidator;

import javax.validation.Constraint;
import javax.validation.constraints.Past;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNegativeValidator.class)
@Past
public @interface NotNegative {

    String message() default "Value < 0";
}
