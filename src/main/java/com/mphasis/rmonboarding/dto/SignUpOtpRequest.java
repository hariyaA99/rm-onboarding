package com.mphasis.rmonboarding.dto;

public class SignUpOtpRequest {
    private String email;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public SignUpOtpRequest(String email){
        this.email = email;
    }
}
