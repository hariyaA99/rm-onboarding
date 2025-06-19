package com.mphasis.rmonboarding.exception;

public class RMNotFoundException extends RuntimeException {

    public RMNotFoundException(String message) {
        super(message);
    }

    public RMNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}