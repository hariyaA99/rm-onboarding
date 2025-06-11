package com.mphasis.rmonboarding.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_details")
public class SignOutSessionDetails {

    @Id
    @Column(name = "username", length = 100, nullable = false)
    private String username;

    @Column(name = "token", columnDefinition = "TEXT", nullable = true)
    private String token;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public SignOutSessionDetails() {}

    public SignOutSessionDetails(String username, String token, LocalDateTime createdAt) {
        this.username = username;
        this.token = token;
        this.createdAt = createdAt;
    }


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
