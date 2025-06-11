package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.LoginOtpRequest;
import com.mphasis.rmonboarding.dto.LoginOtpVerificationRequest;
import com.mphasis.rmonboarding.feign.SignInOtpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class SignInOtpController {

    @Autowired
    private SignInOtpClient signInOtpClient;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody LoginOtpRequest request) {

        String response = signInOtpClient.sendOtp(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody LoginOtpVerificationRequest request) {
        String response = signInOtpClient.verifyOtp(request);
        return ResponseEntity.ok(response);

    }
}