package com.mphasis.rmonboarding.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
