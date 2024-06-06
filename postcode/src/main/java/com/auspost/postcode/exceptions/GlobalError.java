package com.auspost.postcode.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

// privates a unified approach to Error Messages
public class GlobalError {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String errorMessage;
    private String exceptionClass;
    private String debugMessage;

    public GlobalError(HttpStatus status, Throwable ex) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.errorMessage = "";
        this.exceptionClass = ex.getClass().getSimpleName();
        this.debugMessage = ex.getLocalizedMessage();
    }

    // return the debug message for the logger
    public HttpStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getExceptionClass() {
        return this.exceptionClass;
    }

    public String getDebugMessage() {
        return this.debugMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.errorMessage = ErrorMessage;
    }

}
