package com.auspost.postcode.exceptions;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// makes it the central point for handling exceptions within the controller
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger fullLogsLogger = LogManager.getLogger("fullLogs");
    private static final Logger errorLogger = LogManager.getLogger("error");

    // helper method to create a user facing error message and log the error
    private ResponseEntity<GlobalError> createErrorResponse(HttpStatus status, Exception ex, String message) {
        GlobalError error = new GlobalError(status, ex);
        error.setErrorMessage(message);
        fullLogsLogger.error(error.getDebugMessage());
        errorLogger.error(error.getDebugMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    // Handle exceptions that return a 400 error (not found error)
    // eg can't find by ID
    @ExceptionHandler({ NoSuchElementException.class })
    public ResponseEntity<GlobalError> handleNotFoundEntity(Exception ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex,
                "The requested resource could not be found. Please verify ID or parameters.");
    }

    // Handle exceptions that return a 400 error (bad request)
    @ExceptionHandler({ HttpMessageNotReadableException.class, MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class })
    public ResponseEntity<GlobalError> handleBadRequestExceptions(Exception ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex,
                "Invalid request. Please check the request and try again.");
    }

    // Handle other errors with a generic message
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalError> handleGenericException(Exception ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex,
                "An unexpected error occurred on the server. Please try again later.");
    }

    // Handle service level errors caught in the controller
    // already returning a JSON object
    @ExceptionHandler(ServiceValidationException.class)
    public ResponseEntity<String> handleServiceValidationException(ServiceValidationException ex) {
        String errorJson = ex.generateMessage();
        fullLogsLogger.error(errorJson);
        errorLogger.error(errorJson);
        return new ResponseEntity<>(errorJson, HttpStatus.BAD_REQUEST);
    }
}
