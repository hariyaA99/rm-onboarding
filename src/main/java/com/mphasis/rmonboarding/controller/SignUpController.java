package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.feign.SignUpOtpClient;
import com.mphasis.rmonboarding.dto.SignUpOtpRequest;
import com.mphasis.rmonboarding.dto.SignUpVerifyOtpRequest;
import com.mphasis.rmonboarding.exception.OtpSendException;
import com.mphasis.rmonboarding.exception.OtpVerifyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class SignUpController {

    @Autowired
    private SignUpOtpClient signUpOtpClient;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody SignUpOtpRequest request) {
        try {
            String response = signUpOtpClient.sendOtp(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new OtpSendException("Failed to send OTP", e);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody SignUpVerifyOtpRequest request) {
        try {
            String response = signUpOtpClient.verifyOtp(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new OtpVerifyException("Failed to verify OTP", e);
        }
    }
}
