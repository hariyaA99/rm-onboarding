package com.mphasis.rmonboarding.entity;


import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class ValidateRMUser {

    @Id
    @Column(name = "rm_code", length = 20)
    private String rmCode;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "password_updated_at")
    private Timestamp passwordUpdatedAt;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "modified_on")
    private Timestamp modifiedOn;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    // --- Constructors ---
    public ValidateRMUser() {}

    // --- Getters and Setters ---
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

    public Timestamp getPasswordUpdatedAt() {
        return passwordUpdatedAt;
    }

    public void setPasswordUpdatedAt(Timestamp passwordUpdatedAt) {
        this.passwordUpdatedAt = passwordUpdatedAt;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
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
