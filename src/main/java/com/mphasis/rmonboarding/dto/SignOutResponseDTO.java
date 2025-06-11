package com.mphasis.rmonboarding.dto;

import java.time.LocalDateTime;

public class SignOutResponseDTO {
    private boolean success;
    private String message;
    private String timestamp;
    private String username;
    private boolean expired; // New field to indicate if session expired

    public SignOutResponseDTO() {
        this.timestamp = LocalDateTime.now().toString();
    }

    public SignOutResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public SignOutResponseDTO(boolean success, String message, String username) {
        this.success = success;
        this.message = message;
        this.username = username;
        this.timestamp = LocalDateTime.now().toString();
    }

    public SignOutResponseDTO(boolean success, String message, String username, boolean expired) {
        this.success = success;
        this.message = message;
        this.username = username;
        this.expired = expired;
        this.timestamp = LocalDateTime.now().toString();
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public boolean isExpired() { return expired; }
    public void setExpired(boolean expired) { this.expired = expired; }
}
