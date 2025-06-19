package com.mphasis.rmonboarding.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "session_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInSessionDetails {

    @Id
    @Column(name="username",nullable = false, length = 100)
    private String username;

    @Column(name="token",columnDefinition = "TEXT")
    private String token;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();


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
