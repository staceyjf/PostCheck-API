package com.auspost.postcode.exceptions;

import java.util.ArrayList;
import java.util.Map;

public class ServiceValidationException extends Exception {

    private ValidationErrors errors;

    public ServiceValidationException(ValidationErrors errors) {
        super("Service validation error occurred");
        this.errors = errors;
    }

    public Map<String, ArrayList<String>> getErrors() {
        return errors.getErrors();
    }
}