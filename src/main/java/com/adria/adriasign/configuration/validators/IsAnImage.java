package com.adria.adriasign.configuration.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = IsAnImageValidator.class)
//constrains can be declared on fields, constructor parameters, and setters.
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsAnImage {
    String message() default "{IsAnImage.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
