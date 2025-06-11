package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.entity.SignOutSessionDetails;
import com.mphasis.rmonboarding.exception.DatabaseOperationException;
import com.mphasis.rmonboarding.exception.SessionNotFoundException;
import com.mphasis.rmonboarding.exception.ValidationException;
import com.mphasis.rmonboarding.repository.SignOutSessionDetailsRepository;
import com.mphasis.rmonboarding.service.interfaces.SignOutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class SignOutServiceImpl implements SignOutService {

    private static final Logger logger = LoggerFactory.getLogger(SignOutServiceImpl.class);

    @Autowired
    private SignOutSessionDetailsRepository signOutSessionDetailsRepository;

    @Override
    @Transactional
    public boolean signOut(String token) {
        logger.info("Attempting to sign out with token");

        try {
            validateToken(token);

            Optional<SignOutSessionDetails> sessionOpt = signOutSessionDetailsRepository.findByActiveToken(token);

            if (sessionOpt.isEmpty()) {
                logger.warn("Session not found for provided token during sign out");
                throw new SessionNotFoundException("Session not found or already signed out");
            }

            SignOutSessionDetails session = sessionOpt.get();
            String username = session.getUsername();

            signOutSessionDetailsRepository.delete(session);
            logger.info("Successfully signed out user: {}", username);
            return true;

        } catch (ValidationException | SessionNotFoundException e) {
            // Re-throw business logic exceptions as-is
            logger.error("Business logic error during sign out: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            logger.error("Database error during sign out", e);
            throw new DatabaseOperationException("Failed to sign out due to database error", e);
        } catch (Exception e) {
            logger.error("Unexpected error during sign out", e);
            throw new DatabaseOperationException("Unexpected error during sign out", e);
        }
    }

    @Override
    public boolean isTokenActive(String token) {
        logger.debug("Checking if token is active");

        try {
            validateToken(token);
            boolean isActive = signOutSessionDetailsRepository.existsByActiveToken(token);
            logger.debug("Token active status: {}", isActive);
            return isActive;

        } catch (ValidationException e) {
            // Re-throw business logic exceptions as-is
            throw e;
        } catch (DataAccessException e) {
            logger.error("Database error while checking token status", e);
            throw new DatabaseOperationException("Failed to check token status", e);
        } catch (Exception e) {
            logger.error("Unexpected error while checking token status", e);
            throw new DatabaseOperationException("Failed to check token status", e);
        }
    }

    @Override
    public Optional<SignOutSessionDetails> getSessionByToken(String token) {
        logger.debug("Retrieving session by token");

        try {
            validateToken(token);
            Optional<SignOutSessionDetails> session = signOutSessionDetailsRepository.findByActiveToken(token);
            logger.debug("Session found: {}", session.isPresent());
            return session;

        } catch (ValidationException e) {
            // Re-throw business logic exceptions as-is
            throw e;
        } catch (DataAccessException e) {
            logger.error("Database error while retrieving session", e);
            throw new DatabaseOperationException("Failed to retrieve session", e);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving session", e);
            throw new DatabaseOperationException("Failed to retrieve session", e);
        }
    }

    private void validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new ValidationException("Token cannot be null or empty");
        }
    }

    private void validateInputsForSession(String username, String token) {
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