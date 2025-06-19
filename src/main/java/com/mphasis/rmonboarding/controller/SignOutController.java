package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.SignOutResponseDTO;
import com.mphasis.rmonboarding.entity.SignOutSessionDetails;
import com.mphasis.rmonboarding.exception.InvalidTokenException;
import com.mphasis.rmonboarding.exception.SessionNotFoundException;
import com.mphasis.rmonboarding.service.interfaces.SignOutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SignOutController {

    private static final Logger logger = LoggerFactory.getLogger(SignOutController.class);

    @Autowired
    private SignOutService signOutService;

    @PostMapping("/signout")
    public ResponseEntity<SignOutResponseDTO> signOut(@RequestHeader("Authorization") String authHeader) {
        logger.info("Sign out request received");

        // Validate authorization header
        validateAuthorizationHeader(authHeader);

        String token = extractTokenFromHeader(authHeader);

        // Getting session details to retrieve username before signing out
        Optional<SignOutSessionDetails> sessionOpt = signOutService.getSessionByToken(token);
        if (sessionOpt.isEmpty()) {
            logger.warn("Session not found for sign out request");
            throw new SessionNotFoundException("Session not found or already signed out");
        }

        String username = sessionOpt.get().getUsername();
        boolean success = signOutService.signOut(token);

        if (!success) {
            logger.error("Sign out failed for username: {}", username);
            throw new SessionNotFoundException("Session not found or already signed out");
        }

        logger.info("Successfully signed out user: {}", username);
        return ResponseEntity.ok(new SignOutResponseDTO(true, "Successfully signed out", username));
    }

    @PostMapping("/check-session")
    public ResponseEntity<String> checkSession(@RequestHeader("Authorization") String authHeader) {
        logger.info("Check session request received");

        // Validating authorization header
        validateAuthorizationHeader(authHeader);

        String token = extractTokenFromHeader(authHeader);
        boolean isActive = signOutService.isTokenActive(token);

        if (isActive) {
            Optional<SignOutSessionDetails> session = signOutService.getSessionByToken(token);
            if (session.isPresent()) {
                String username = session.get().getUsername();
                logger.info("Session active for user: {}", username);
                return ResponseEntity.ok("Session active for user: " + username);
            }
        }

        logger.info("Session not active or token invalid");
        return ResponseEntity.status(401).body("Session not active or token invalid");
    }

    private void validateAuthorizationHeader(String authHeader) {
        if (!StringUtils.hasText(authHeader)) {
            logger.error("Authorization header is missing");
            throw new InvalidTokenException("Authorization header is required");
        }

        if (!authHeader.startsWith("Bearer ")) {
            logger.error("Invalid authorization header format");
            throw new InvalidTokenException("Invalid Authorization header format. Must start with 'Bearer '");
        }
    }

    private String extractTokenFromHeader(String authHeader) {
        String token = authHeader.substring(7);
        if (!StringUtils.hasText(token)) {
            logger.error("Token is empty in authorization header");
            throw new InvalidTokenException("Token cannot be empty");
        }
        return token;
    }
}