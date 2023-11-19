package org.example.validate;

import org.example.validate.validator.MinLengthValidator;

import javax.validation.Constraint;
import javax.validation.constraints.Past;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinLengthValidator.class)
@Past
public @interface MinLength {

    String message() default "Min length {value}";

    String value() default "2";
}
