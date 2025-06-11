package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.entity.SignOutSessionDetails;
import com.mphasis.rmonboarding.exception.DatabaseOperationException;
import com.mphasis.rmonboarding.exception.SessionNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignOutServiceImplTest {

    @Mock
    private SignOutSessionDetailsRepository signOutSessionDetailsRepository;

    @InjectMocks
    private SignOutServiceImpl signOutService;

    @Test
    void signOut_WithValidToken_ShouldReturnTrueAndDeleteSession() {
        String token = "validtoken123";
        String username = "testuser";

        SignOutSessionDetails session = new SignOutSessionDetails();
        session.setToken(token);
        session.setUsername(username);
        session.setCreatedAt(LocalDateTime.now());

        when(signOutSessionDetailsRepository.findByActiveToken(token))
                .thenReturn(Optional.of(session));

        boolean result = signOutService.signOut(token);

        assertTrue(result);
        verify(signOutSessionDetailsRepository).findByActiveToken(token);
        verify(signOutSessionDetailsRepository).delete(session);
    }

    @Test
    void signOut_WithInvalidToken_ShouldThrowSessionNotFoundException() {
        String token = "invalidtoken123";

        when(signOutSessionDetailsRepository.findByActiveToken(token))
                .thenReturn(Optional.empty());

        SessionNotFoundException exception = assertThrows(SessionNotFoundException.class,
                () -> signOutService.signOut(token));

        assertEquals("Session not found or already signed out", exception.getMessage());
        verify(signOutSessionDetailsRepository, never()).delete(any());
    }

    @Test
    void signOut_WithNullOrEmptyToken_ShouldThrowValidationException() {
        String token = null;

        ValidationException exception = assertThrows(ValidationException.class,
                () -> signOutService.signOut(token));

        assertEquals("Token cannot be null or empty", exception.getMessage());
        verify(signOutSessionDetailsRepository, never()).findByActiveToken(anyString());
    }

    @Test
    void isTokenActive_WithValidActiveToken_ShouldReturnTrue() {
        String token = "validtoken123";

        when(signOutSessionDetailsRepository.existsByActiveToken(token))
                .thenReturn(true);

        boolean result = signOutService.isTokenActive(token);

        assertTrue(result);
        verify(signOutSessionDetailsRepository).existsByActiveToken(token);
    }

    @Test
    void getSessionByToken_WithValidToken_ShouldReturnSession() {
        String token = "validtoken123";
        String username = "testuser";

        SignOutSessionDetails session = new SignOutSessionDetails();
        session.setToken(token);
        session.setUsername(username);

        when(signOutSessionDetailsRepository.findByActiveToken(token))
                .thenReturn(Optional.of(session));

        Optional<SignOutSessionDetails> result = signOutService.getSessionByToken(token);

        assertTrue(result.isPresent());
        assertEquals(token, result.get().getToken());
        assertEquals(username, result.get().getUsername());
    }

    @Test
    void signOut_WithDatabaseError_ShouldThrowDatabaseOperationException() {
        String token = "validtoken123";

        when(signOutSessionDetailsRepository.findByActiveToken(token))
                .thenThrow(new DataAccessException("Database connection failed") {});

        DatabaseOperationException exception = assertThrows(DatabaseOperationException.class,
                () -> signOutService.signOut(token));

        assertEquals("Failed to sign out due to database error", exception.getMessage());
    }
}
