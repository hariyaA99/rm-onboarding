package com.mphasis.rmonboarding.service.interfaces;

import com.mphasis.rmonboarding.dto.TokenValidationResponseDTO;

public interface TokenValidationService {
    TokenValidationResponseDTO validateToken(String username, String token);
}