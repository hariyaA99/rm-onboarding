// File: com.mphasis.rmonboarding.dto.RMEditRequestDTO.java
package com.mphasis.rmonboarding.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RMEditRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must be at most 100 characters")
    private String email;

    @Size(max = 10, message = "Phone must be at most 10 digits")
    private String phone;

    @Size(max = 10, message = "Alternate phone must be at most 10 digits")
    private String alternatePhone;

    private LocalDate dob;

    public @NotBlank(message = "Name is required") @Size(max = 100, message = "Name must be at most 100 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") @Size(max = 100, message = "Name must be at most 100 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Invalid email format") @Size(max = 100, message = "Email must be at most 100 characters") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") @Size(max = 100, message = "Email must be at most 100 characters") String email) {
        this.email = email;
    }

    public @Size(max = 10, message = "Phone must be at most 10 digits") String getPhone() {
        return phone;
    }

    public void setPhone(@Size(max = 10, message = "Phone must be at most 10 digits") String phone) {
        this.phone = phone;
    }

    public @Size(max = 10, message = "Alternate phone must be at most 10 digits") String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(@Size(max = 10, message = "Alternate phone must be at most 10 digits") String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public @Size(max = 100, message = "Location must be at most 100 characters") String getLocation() {
        return location;
    }

    public void setLocation(@Size(max = 100, message = "Location must be at most 100 characters") String location) {
        this.location = location;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Size(max = 100, message = "Location must be at most 100 characters")
    private String location;

    private String photoBase64;

    private String modifiedBy;
}
