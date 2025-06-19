package com.mphasis.rmonboarding.dto;

public class RmCodeCheckResponse {
    private boolean rmCodeExists;
    private String email;
    private boolean emailExists;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RmCodeCheckResponse() {}

    public RmCodeCheckResponse(boolean rmCodeExists, String email, boolean emailExists,String message) {
        this.rmCodeExists = rmCodeExists;
        this.email = email;
        this.emailExists = emailExists;
        this.message = message;
    }

    public boolean isRmCodeExists() {
        return rmCodeExists;
    }

    public void setRmCodeExists(boolean rmCodeExists) {
        this.rmCodeExists = rmCodeExists;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailExists() {
        return emailExists;
    }

    public void setEmailExists(boolean emailExists) {
        this.emailExists = emailExists;
    }
}
