package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.SignOutResponseDTO;
import com.mphasis.rmonboarding.entity.SignOutSessionDetails;
import com.mphasis.rmonboarding.exception.InvalidTokenException;
import com.mphasis.rmonboarding.exception.SessionNotFoundException;
import com.mphasis.rmonboarding.service.interfaces.SignOutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignOutControllerTest {

    @Mock
    private SignOutService signOutService;

    @InjectMocks
    private SignOutController signOutController;

    @Test
    void signOut_WithValidToken_ShouldReturnSuccessResponse() {
        String authHeader = "Bearer validtoken123";
        String token = "validtoken123";
        String username = "testuser";

        SignOutSessionDetails mockSession = new SignOutSessionDetails();
        mockSession.setUsername(username);
        mockSession.setToken(token);
        mockSession.setCreatedAt(LocalDateTime.now());

        when(signOutService.getSessionByToken(token)).thenReturn(Optional.of(mockSession));
        when(signOutService.signOut(token)).thenReturn(true);

        ResponseEntity<SignOutResponseDTO> response = signOutController.signOut(authHeader);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Successfully signed out", response.getBody().getMessage());
        verify(signOutService).getSessionByToken(token);
        verify(signOutService).signOut(token);
    }

    @Test
    void signOut_WithMissingAuthorizationHeader_ShouldThrowInvalidTokenException() {
        String authHeader = null;

        InvalidTokenException exception = assertThrows(InvalidTokenException.class,
                () -> signOutController.signOut(authHeader));

        assertEquals("Authorization header is required", exception.getMessage());
        verify(signOutService, never()).getSessionByToken(anyString());
    }

    @Test
    void signOut_WithInvalidAuthorizationFormat_ShouldThrowInvalidTokenException() {
        String authHeader = "Basic validtoken123";

        InvalidTokenException exception = assertThrows(InvalidTokenException.class,
                () -> signOutController.signOut(authHeader));

        assertEquals("Invalid Authorization header format. Must start with 'Bearer '", exception.getMessage());
        verify(signOutService, never()).getSessionByToken(anyString());
    }

    @Test
    void signOut_WithSessionNotFound_ShouldThrowSessionNotFoundException() {
        String authHeader = "Bearer validtoken123";
        String token = "validtoken123";

        when(signOutService.getSessionByToken(token)).thenReturn(Optional.empty());

        SessionNotFoundException exception = assertThrows(SessionNotFoundException.class,
                () -> signOutController.signOut(authHeader));

        assertEquals("Session not found or already signed out", exception.getMessage());
        verify(signOutService).getSessionByToken(token);
        verify(signOutService, never()).signOut(anyString());
    }

    @Test
    void checkSession_WithActiveSession_ShouldReturnActiveMessage() {
        // Arrange
        String authHeader = "Bearer validtoken123";
        String token = "validtoken123";
        String username = "testuser";

        SignOutSessionDetails mockSession = new SignOutSessionDetails();
        mockSession.setUsername(username);

        when(signOutService.isTokenActive(token)).thenReturn(true);
        when(signOutService.getSessionByToken(token)).thenReturn(Optional.of(mockSession));

        ResponseEntity<String> response = signOutController.checkSession(authHeader);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Session active for user: " + username, response.getBody());
    }

    @Test
    void checkSession_WithInactiveToken_ShouldReturnInactiveMessage() {
        String authHeader = "Bearer validtoken123";
        String token = "validtoken123";

        when(signOutService.isTokenActive(token)).thenReturn(false);

        ResponseEntity<String> response = signOutController.checkSession(authHeader);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Session not active or token invalid", response.getBody());
        verify(signOutService, never()).getSessionByToken(anyString());
    }
}