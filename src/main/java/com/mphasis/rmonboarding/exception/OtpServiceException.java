package com.mphasis.rmonboarding.exception;

public class OtpServiceException extends RuntimeException {
    public OtpServiceException(String message) {
        super(message);
    }

    public OtpServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}