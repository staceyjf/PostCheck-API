package com.auspost.postcode.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.lang3.StringUtils;

// ConstraintValidator interface is responsible for validating a field annotation with @NumericValidation
public class isNumericValidator implements ConstraintValidator<NumericValidation, String> {
    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");

    @Override
    public void initialize(NumericValidation constraintAnnotation) {
    }

    // Overriding the isValid method to check if the postcode can be converted to a
    // number.
    @Override
    public boolean isValid(String postcode, ConstraintValidatorContext context) {
        boolean isNumeric = StringUtils.isNumeric(postcode);
        fullLogsLogger.info("Postcode: {}, isNumeric: {}", postcode, isNumeric);
        return isNumeric;
    }
}
