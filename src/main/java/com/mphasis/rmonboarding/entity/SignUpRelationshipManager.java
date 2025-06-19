package com.mphasis.rmonboarding.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "relationship_manager")
public class SignUpRelationshipManager {

    @Id
    @Column(name = "rm_code")
    private String rmCode;

    private String name;
    private String email;
    private String phone;
    private LocalDate dob;
    private String location;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "photo_base64",columnDefinition = "LONGTEXT")
    private String photoBase64;

    @Column(name = "created_on")
    private LocalDateTime createdOn;


    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "modified_by")
    private String modifiedBy;


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

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
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
