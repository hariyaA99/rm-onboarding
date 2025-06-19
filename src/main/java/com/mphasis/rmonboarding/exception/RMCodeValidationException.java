
package com.mphasis.rmonboarding.exception;

public class RMCodeValidationException extends RuntimeException {
    public RMCodeValidationException(String message) {
        super(message);
    }

    public RMCodeValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}