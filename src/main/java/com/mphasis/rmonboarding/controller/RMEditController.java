// File: RMEditController.java
package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.RMEditRequestDTO;
import com.mphasis.rmonboarding.dto.TokenValidationResponseDTO;
import com.mphasis.rmonboarding.entity.RegistrationRelationshipManager;
import com.mphasis.rmonboarding.exception.InvalidTokenException;
import com.mphasis.rmonboarding.exception.RMNotFoundException;
import com.mphasis.rmonboarding.service.interfaces.RMEditService;
import com.mphasis.rmonboarding.service.interfaces.TokenValidationService;
import com.mphasis.rmonboarding.repository.RegistrationRelationshipManagerRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping("/api/rm")
@Validated
public class RMEditController {

    private final RMEditService rmEditService;
    private final TokenValidationService tokenValidationService;
    private final RegistrationRelationshipManagerRepository rmRepository;
    private final EntityManager entityManager;

    public RMEditController(RMEditService rmEditService,
                            TokenValidationService tokenValidationService,
                            RegistrationRelationshipManagerRepository rmRepository,
                            EntityManager entityManager) {
        this.rmEditService = rmEditService;
        this.tokenValidationService = tokenValidationService;
        this.rmRepository = rmRepository;
        this.entityManager = entityManager;
    }

    @GetMapping("/profile")
    public ResponseEntity<RegistrationRelationshipManager> getProfile(@Valid @RequestHeader HttpHeaders headers)
    {
        validateToken(headers);
        String username = extractUsernameFromHeader(headers);
        String rmCode = getRMCodeFromUsername(username);
        RegistrationRelationshipManager profile = entityManager.find(RegistrationRelationshipManager.class, rmCode);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }
    @PostMapping("/edit")
    public ResponseEntity<RegistrationRelationshipManager> editProfile(
            @Valid @RequestBody RMEditRequestDTO request,
            @RequestHeader HttpHeaders headers) {

        validateToken(headers); // ✅ Token validation

        // Get username from headers
        String username = extractUsernameFromHeader(headers);

        // Dynamically fetch rmCode based on username (email)
        String rmCode = getRMCodeFromUsername(username);

        RegistrationRelationshipManager updatedRM = rmEditService.editProfile(rmCode, request);
        return ResponseEntity.ok(updatedRM);
    }

    // 🔍 New method to dynamically get rmCode from username
    private String getRMCodeFromUsername(String username) {
        RegistrationRelationshipManager rm = rmRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new RMNotFoundException(
                        "Relationship Manager not found for email: " + username));
        return rm.getRmCode();
    }

    // 🔐 Shared helper method to validate token
    private void validateToken(HttpHeaders headers) {
        String username = extractUsernameFromHeader(headers);
        String token = extractTokenFromHeader(headers);

        TokenValidationResponseDTO response = tokenValidationService.validateToken(username, token);

        if (!response.isValid()) {
            throw new InvalidTokenException("Invalid or expired token: " + response.getMessage());
        }
    }

    private String extractUsernameFromHeader(HttpHeaders headers) {
        String username = headers.getFirst("Username");

        if (!StringUtils.hasText(username)) {
            throw new InvalidTokenException("Username header is required");
        }

        return username.trim();
    }

    private String extractTokenFromHeader(HttpHeaders headers) {
        String authHeader = headers.getFirst("Authorization");

        if (!StringUtils.hasText(authHeader)) {
            throw new InvalidTokenException("Authorization header is required");
        }

        if (!authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid Authorization header format. Must start with 'Bearer '");
        }

        String token = authHeader.substring(7).trim();
        if (!StringUtils.hasText(token)) {
            throw new InvalidTokenException("Token cannot be empty");
        }

        return token;
    }
}