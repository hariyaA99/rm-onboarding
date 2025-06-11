package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.TokenValidationResponseDTO;
import com.mphasis.rmonboarding.exception.InvalidTokenException;
import com.mphasis.rmonboarding.service.interfaces.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/api")
public class TokenValidationController {

    @Autowired
    private TokenValidationService tokenValidationService;

    @PostMapping("/validate-token")
    public ResponseEntity<TokenValidationResponseDTO> validateToken(@RequestHeader HttpHeaders headers) {
        try {
            String username = extractUsernameFromHeader(headers);
            String token = extractTokenFromHeader(headers);

            TokenValidationResponseDTO response = tokenValidationService.validateToken(username, token);

            return ResponseEntity.ok(new TokenValidationResponseDTO(
                    response.isValid(),
                    response.getMessage()
            ));

        } catch (InvalidTokenException e) {
            return ResponseEntity.ok(new TokenValidationResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(new TokenValidationResponseDTO(false, "Token validation failed"));
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