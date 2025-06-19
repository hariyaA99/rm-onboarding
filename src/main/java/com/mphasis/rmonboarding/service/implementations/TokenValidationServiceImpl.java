package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.dto.TokenValidationResponseDTO;
import com.mphasis.rmonboarding.entity.SignOutSessionDetails;
import com.mphasis.rmonboarding.exception.DatabaseOperationException;
import com.mphasis.rmonboarding.exception.ValidationException;
import com.mphasis.rmonboarding.repository.SignOutSessionDetailsRepository;
import com.mphasis.rmonboarding.service.interfaces.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenValidationServiceImpl implements TokenValidationService {

    @Autowired
    private SignOutSessionDetailsRepository sessionRepo;

    @Override
    public TokenValidationResponseDTO validateToken(String username, String token) {
        validateInputs(username, token);

        try {
            Optional<SignOutSessionDetails> sessionOpt = sessionRepo.findByUsernameAndToken(username, token);

            if (sessionOpt.isEmpty()) {
                return new TokenValidationResponseDTO(false, "Invalid username or token", null, null, null);
            }

            SignOutSessionDetails session = sessionOpt.get();
            LocalDateTime now = LocalDateTime.now();

            if (session.getCreatedAt().plusMinutes(30).isBefore(now)) {
                try {
                    sessionRepo.delete(session);
                    return new TokenValidationResponseDTO(false, "Session has expired", username, token, session.getCreatedAt());
                } catch (DataAccessException e) {
                    throw new DatabaseOperationException("Failed to clean up expired session", e);
                }
            }

            return new TokenValidationResponseDTO(true, "Session is active", username, token, session.getCreatedAt());

        } catch (DatabaseOperationException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error during token validation", e);
        } catch (Exception e) {
            throw new DatabaseOperationException("Unexpected error during token validation", e);
        }
    }

    private void validateInputs(String username, String token) {
        if (!StringUtils.hasText(username)) {
            throw new ValidationException("Username cannot be null or empty");
        }

        if (!StringUtils.hasText(token)) {
            throw new ValidationException("Token cannot be null or empty");
        }

        if (username.trim().length() > 100) {
            throw new ValidationException("Username exceeds maximum length of 100 characters");
        }
    }
}
