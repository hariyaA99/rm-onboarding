package com.mphasis.rmonboarding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class PasswordResetUsers {

    @Id
    @Column(name = "rm_code", length = 20)
    private String rmCode;

    @Column(name="username",length = 100)
    private String username;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(name = "password_updated_at")
    private LocalDateTime passwordUpdatedAt;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    // Getters and Setters
    public String getRmCode() {
        return rmCode;
    }

    public void setRmCode(String rmCode) {
        this.rmCode = rmCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getPasswordUpdatedAt() {
        return passwordUpdatedAt;
    }

    public void setPasswordUpdatedAt(LocalDateTime passwordUpdatedAt) {
        this.passwordUpdatedAt = passwordUpdatedAt;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}

