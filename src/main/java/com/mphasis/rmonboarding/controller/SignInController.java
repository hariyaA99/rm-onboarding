package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.LoginRequestDTO;
import com.mphasis.rmonboarding.dto.LoginWithOtpDTO;
import com.mphasis.rmonboarding.entity.SignInUsers;
import com.mphasis.rmonboarding.service.interfaces.SignInService;
import com.mphasis.rmonboarding.service.interfaces.SignInSessionDetailsService;
import com.mphasis.rmonboarding.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mphasis.rmonboarding.dto.LoginOtpRequest;
import com.mphasis.rmonboarding.feign.SignInOtpClient;
import java.util.*;
import com.mphasis.rmonboarding.dto.LoginOtpVerificationRequest;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class SignInController {

    private final SignInService signInService;
    private final SignInOtpClient signInOtpClient;
    private final JwtUtil jwtUtil;


    @Autowired
    private SignInSessionDetailsService signInSessionDetailsService;

    public SignInController(SignInOtpClient signInOtpClient,
                            SignInService signInService,
                            JwtUtil jwtUtil) {
        this.signInOtpClient = signInOtpClient;
        this.signInService = signInService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateUser(@RequestBody LoginRequestDTO loginRequest) {
        try {
            SignInUsers signInUsers = signInService.validateUser(loginRequest.getEmail(), loginRequest.getPassword());

            LoginOtpRequest loginOtpRequest = new LoginOtpRequest();
            loginOtpRequest.setEmail(loginRequest.getEmail());
            String otpResponse = signInOtpClient.sendOtp(loginOtpRequest);

            return ResponseEntity.ok(otpResponse);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if ("BLOCKED".equals(e.getMessage())) {
                return ResponseEntity.status(401).body("Cannot sign in. Please try again later ❌");
            } else if ("INVALID_PASSWORD".equals(e.getMessage())) {
                return ResponseEntity.status(401).body("Invalid email or password ❌");
            } else {
                return ResponseEntity.status(401).body("Invalid email or password ❌");
            }
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginWithOtp(@RequestBody LoginWithOtpDTO request) {
        try {
            // Step 1: Call Feign client to verify OTP
            LoginOtpVerificationRequest loginOtpVerificationRequest = new LoginOtpVerificationRequest();
            loginOtpVerificationRequest.setEmail(request.getEmail());
            loginOtpVerificationRequest.setOtp(request.getOtp());

            String otpVerificationResponse  = signInOtpClient.verifyOtp(loginOtpVerificationRequest); // Call to OTP microservice

            // Step 2: Check response from OTP service
            if (!"OTP verified successfully.".equalsIgnoreCase(otpVerificationResponse )) {
                return ResponseEntity.status(401).body("OTP verification failed ❌");
            }

            // Step 3: Fetch user and check if blocked
            SignInUsers user = signInService.findByEmail(request.getEmail());
            if (user.isBlocked()) {
                return ResponseEntity.status(403).body("Account is blocked. Try again later.");
            }


                // ✅ Step 3: Generate JWT and save session
            String jwtToken = jwtUtil.generateToken(user.getUsername());
            signInSessionDetailsService.saveSession(user.getUsername(), jwtToken);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful ✅");
            response.put("token", jwtToken);
            response.put("username", user.getUsername());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error during login.");
        }
    }


}
