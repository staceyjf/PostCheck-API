package com.auspost.postcode.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ ElementType.FIELD })
@Constraint(validatedBy = isNumericValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NumericValidation {
    String message() default "Invalid value - postcodes must be numeric";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
