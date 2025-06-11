package com.mphasis.rmonboarding.dto;

import jakarta.validation.constraints.NotBlank;

public class TokenValidationRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Token is required")
    private String token;

    public TokenValidationRequestDTO() {}

    public TokenValidationRequestDTO(String username, String token) {
        this.username = username;
        this.token = token;
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
}