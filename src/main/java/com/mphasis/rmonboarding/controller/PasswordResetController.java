package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.PasswordResetRequest;
import com.mphasis.rmonboarding.service.interfaces.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        return passwordResetService.resetPassword(request);
    }
}
