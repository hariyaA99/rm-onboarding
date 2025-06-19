package com.mphasis.rmonboarding.dto;

import java.time.LocalDateTime;

public class TokenValidationResponseDTO {
    private boolean valid;
    private String message;
    private String username;
    private String token;
    private LocalDateTime createdAt;

    public TokenValidationResponseDTO() {
    }

    public TokenValidationResponseDTO(boolean valid, String message, String username, String token, LocalDateTime createdAt) {
        this.valid = valid;
        this.message = message;
        this.username = username;
        this.token = token;
        this.createdAt = createdAt;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
