package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.TokenValidationResponseDTO;
import com.mphasis.rmonboarding.exception.InvalidTokenException;
import com.mphasis.rmonboarding.service.interfaces.TokenValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenValidationControllerTest {

    @Mock
    private TokenValidationService tokenValidationService;

    @InjectMocks
    private TokenValidationController tokenValidationController;

    private HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        httpHeaders = new HttpHeaders();
    }

    @Test
    void validateToken_WithValidHeadersAndValidToken_ShouldReturnSuccessResponse() {
        httpHeaders.set("Username", "testuser");
        httpHeaders.set("Authorization", "Bearer validtoken123");

        TokenValidationResponseDTO mockResponse = new TokenValidationResponseDTO(true, "Token is valid");
        when(tokenValidationService.validateToken("testuser", "validtoken123"))
                .thenReturn(mockResponse);

        ResponseEntity<TokenValidationResponseDTO> response = tokenValidationController.validateToken(httpHeaders);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isValid());
        assertEquals("Token is valid", response.getBody().getMessage());
    }

    @Test
    void validateToken_WithValidHeadersAndInvalidToken_ShouldReturnFailureResponse() {
        httpHeaders.set("Username", "testuser");
        httpHeaders.set("Authorization", "Bearer invalidtoken");

        TokenValidationResponseDTO mockResponse = new TokenValidationResponseDTO(false, "Invalid token");
        when(tokenValidationService.validateToken("testuser", "invalidtoken"))
                .thenReturn(mockResponse);

        ResponseEntity<TokenValidationResponseDTO> response = tokenValidationController.validateToken(httpHeaders);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isValid());
        assertEquals("Invalid token", response.getBody().getMessage());
    }

    @Test
    void validateToken_WithMissingUsernameHeader_ShouldReturnFailureResponse() {
        httpHeaders.set("Authorization", "Bearer validtoken123");

        ResponseEntity<TokenValidationResponseDTO> response = tokenValidationController.validateToken(httpHeaders);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isValid());
        assertEquals("Username header is required", response.getBody().getMessage());
        verify(tokenValidationService, never()).validateToken(anyString(), anyString());
    }

    @Test
    void validateToken_WithMissingAuthorizationHeader_ShouldReturnFailureResponse() {
        httpHeaders.set("Username", "testuser");

        ResponseEntity<TokenValidationResponseDTO> response = tokenValidationController.validateToken(httpHeaders);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isValid());
        assertEquals("Authorization header is required", response.getBody().getMessage());
        verify(tokenValidationService, never()).validateToken(anyString(), anyString());
    }

    @Test
    void validateToken_WithInvalidAuthorizationFormat_ShouldReturnFailureResponse() {
        httpHeaders.set("Username", "testuser");
        httpHeaders.set("Authorization", "Basic validtoken123");

        ResponseEntity<TokenValidationResponseDTO> response = tokenValidationController.validateToken(httpHeaders);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isValid());
        assertEquals("Invalid Authorization header format. Must start with 'Bearer '", response.getBody().getMessage());
        verify(tokenValidationService, never()).validateToken(anyString(), anyString());
    }

    @Test
    void validateToken_WhenServiceThrowsInvalidTokenException_ShouldReturnFailureResponse() {
        httpHeaders.set("Username", "testuser");
        httpHeaders.set("Authorization", "Bearer validtoken123");

        when(tokenValidationService.validateToken("testuser", "validtoken123"))
                .thenThrow(new InvalidTokenException("Custom token validation error"));

        ResponseEntity<TokenValidationResponseDTO> response = tokenValidationController.validateToken(httpHeaders);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isValid());
        assertEquals("Custom token validation error", response.getBody().getMessage());
    }
}