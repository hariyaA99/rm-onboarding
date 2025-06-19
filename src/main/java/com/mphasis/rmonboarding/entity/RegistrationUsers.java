package com.mphasis.rmonboarding.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class RegistrationUsers {

    @Id
    @Column(name = "rm_code", length = 20)
    private String rmCode;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "password_updated_at")
    private LocalDateTime passwordUpdatedAt;

    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "created_by", length = 50, nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "modified_on", updatable = false)
    private LocalDateTime modifiedOn;

    @Column(name = "modified_by", length = 50, updatable = false)
    private String modifiedBy;



    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdOn = now;
        this.passwordUpdatedAt = now;

        // explicitly setting these as null
        this.modifiedOn = null;
        this.modifiedBy = null;

        this.passwordUpdatedAt = null;
        this.failedLoginAttempts = 0;
        this.isBlocked = false;
    }

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

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
}


