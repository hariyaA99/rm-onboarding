package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.dto.TokenValidationResponseDTO;
import com.mphasis.rmonboarding.entity.SignOutSessionDetails;
import com.mphasis.rmonboarding.exception.DatabaseOperationException;
import com.mphasis.rmonboarding.exception.ValidationException;
import com.mphasis.rmonboarding.repository.SignOutSessionDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenValidationServiceImplTest {

    @Mock
    private SignOutSessionDetailsRepository sessionRepo;

    @InjectMocks
    private TokenValidationServiceImpl tokenValidationService;

    @Test
    void validateToken_WithValidTokenAndActiveSession_ShouldReturnValidResponse() {
        String username = "testuser";
        String token = "validtoken123";

        SignOutSessionDetails session = new SignOutSessionDetails();
        session.setUsername(username);
        session.setToken(token);
        session.setCreatedAt(LocalDateTime.now().minusMinutes(10)); // 10 minutes ago

        when(sessionRepo.findByUsernameAndToken(username, token))
                .thenReturn(Optional.of(session));

        TokenValidationResponseDTO response = tokenValidationService.validateToken(username, token);

        assertNotNull(response);
        assertTrue(response.isValid());
        assertEquals("Session is active", response.getMessage());
        verify(sessionRepo, never()).delete(any());
    }

    @Test
    void validateToken_WithInvalidUsernameOrToken_ShouldReturnInvalidResponse() {
        String username = "testuser";
        String token = "invalidtoken";

        when(sessionRepo.findByUsernameAndToken(username, token))
                .thenReturn(Optional.empty());

        TokenValidationResponseDTO response = tokenValidationService.validateToken(username, token);

        assertNotNull(response);
        assertFalse(response.isValid());
        assertEquals("Invalid username or token", response.getMessage());
    }

    @Test
    void validateToken_WithExpiredSession_ShouldDeleteSessionAndReturnExpiredResponse() {
        String username = "testuser";
        String token = "expiredtoken123";

        SignOutSessionDetails session = new SignOutSessionDetails();
        session.setUsername(username);
        session.setToken(token);
        session.setCreatedAt(LocalDateTime.now().minusMinutes(35)); // 35 minutes ago (expired)

        when(sessionRepo.findByUsernameAndToken(username, token))
                .thenReturn(Optional.of(session));

        TokenValidationResponseDTO response = tokenValidationService.validateToken(username, token);

        assertNotNull(response);
        assertFalse(response.isValid());
        assertEquals("Session has expired", response.getMessage());
        verify(sessionRepo).delete(session);
    }

    @Test
    void validateToken_WithNullOrEmptyInputs_ShouldThrowValidationException() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> tokenValidationService.validateToken(null, "validtoken123"));
        assertEquals("Username cannot be null or empty", exception.getMessage());

        exception = assertThrows(ValidationException.class,
                () -> tokenValidationService.validateToken("testuser", null));
        assertEquals("Token cannot be null or empty", exception.getMessage());
    }

    @Test
    void validateToken_WithUsernameTooLong_ShouldThrowValidationException() {
        String username = "a".repeat(101); // 101 characters
        String token = "validtoken123";

        ValidationException exception = assertThrows(ValidationException.class,
                () -> tokenValidationService.validateToken(username, token));

        assertEquals("Username exceeds maximum length of 100 characters", exception.getMessage());
        verify(sessionRepo, never()).findByUsernameAndToken(any(), any());
    }

    @Test
    void validateToken_WithDatabaseError_ShouldThrowDatabaseOperationException() {
        String username = "testuser";
        String token = "validtoken123";

        when(sessionRepo.findByUsernameAndToken(username, token))
                .thenThrow(new DataAccessException("Database connection failed") {});

        DatabaseOperationException exception = assertThrows(DatabaseOperationException.class,
                () -> tokenValidationService.validateToken(username, token));

        assertEquals("Database error during token validation", exception.getMessage());
    }
}