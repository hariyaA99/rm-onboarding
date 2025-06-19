package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.PasswordResetOtpRequest;
import com.mphasis.rmonboarding.dto.PasswordResetOtpVerification;
import com.mphasis.rmonboarding.feign.PasswordResetOtpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


    @RestController
    @RequestMapping("/user/reset")
    @CrossOrigin
    public class PasswordResetOtpController {

        @Autowired
        private PasswordResetOtpClient PasswordResetOtpClient;

        @PostMapping("/send-otp")
        public ResponseEntity<String> sendOtp(@RequestBody PasswordResetOtpRequest request) {

                String response = PasswordResetOtpClient.sendOtp(request);
                return ResponseEntity.ok(response);

        }

        @PostMapping("/verify-otp")
        public ResponseEntity<String> verifyOtp(@RequestBody PasswordResetOtpVerification request) {

                String response = PasswordResetOtpClient.verifyOtp(request);
                return ResponseEntity.ok(response);

        }
}










