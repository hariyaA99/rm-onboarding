package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.RegistrationDTO;
import com.mphasis.rmonboarding.service.implementations.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")  // Optional for frontend integration
public class RegistrationController {

    @Autowired
    private RegistrationServiceImpl userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationDTO dto) {
        userServiceImpl.register(dto);
        return ResponseEntity.ok("User registered successfully");
    }
}


