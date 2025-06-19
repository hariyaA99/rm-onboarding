package com.mphasis.rmonboarding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "relationship_manager")
public class PasswordResetRelationshipManager {

    @Id
    @Column(name = "rm_code", nullable = false, length = 20)
    @NotBlank(message = "RM code is required")
    private String rmCode;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must be at most 100 characters")
    private String email;

    @Column(length = 20)
    @Size(max = 20, message = "Phone must be at most 20 characters")
    private String phone;

    @Column(name = "alternate_phone", length = 20)
    @Size(max = 20, message = "Alternate phone must be at most 20 characters")
    private String alternatePhone;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(length = 100)
    @Size(max = 100, message = "Location must be at most 100 characters")
    private String location;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "photo_base64", columnDefinition = "LONGTEXT")
    private String photoBase64;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    public @Size(max = 20, message = "Alternate phone must be at most 20 characters") String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(@Size(max = 20, message = "Alternate phone must be at most 20 characters") String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    @Column(name="modified_by")
    private String modifiedBy;
    // Getters and Setters
    public String getRmCode() {
        return rmCode;
    }

    public void setRmCode(String rmCode) {
        this.rmCode = rmCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhotoUrl() {
        return photoBase64;
    }

    public void setPhotoUrl(String photoBase64) {
        this.photoBase64 = photoBase64;
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
}


