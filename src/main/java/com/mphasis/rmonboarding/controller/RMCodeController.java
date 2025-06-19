package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.RmCodeCheckResponse;
import com.mphasis.rmonboarding.feign.SignUpOtpClient;
import com.mphasis.rmonboarding.repository.SignUpRepository;
import com.mphasis.rmonboarding.service.implementations.SignUpServiceImpl;
import com.mphasis.rmonboarding.service.implementations.ValidateRMUserServiceImpl;
import com.mphasis.rmonboarding.service.interfaces.SignUpService;
import com.mphasis.rmonboarding.service.interfaces.ValidateRMUserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@Validated
public class RMCodeController {

    @Autowired
    private ValidateRMUserService validateRMUserService;

    @Autowired
    private SignUpRepository signUpRepository;

    @Autowired
    private SignUpServiceImpl signUpService;

    @GetMapping("/check-rm-email/{rmcode}")
    public ResponseEntity<Map<String, String>> checkRMCodeAndEmail(@PathVariable String rmcode) {
        Map<String, String> response = new HashMap<>();

        String email = validateRMUserService.getEmailByRmCode(rmcode);

        if (email == null) {
            throw new RuntimeException("RM Code not found: " + rmcode);
        }

        // Corrected line: directly check if a user exists with this email as username
        boolean userExists = signUpRepository.existsByUsername(email);

        if (userExists) {
            response.put("status", "exists");
            response.put("message", "User already exists with email " + email);
        } else {
            signUpService.sendOtp(email);
            response.put("status", "otp_sent");
            response.put("message", "OTP sent to " + email);
        }

        response.put("email", email);
        return ResponseEntity.ok(response);
    }
}