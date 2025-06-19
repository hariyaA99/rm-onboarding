package com.mphasis.rmonboarding.service.interfaces;

import com.mphasis.rmonboarding.dto.PasswordResetRequest;
import org.springframework.http.ResponseEntity;

public interface PasswordResetService {
    ResponseEntity<String> verifyOtp(PasswordResetRequest request);
    ResponseEntity<String> resetPassword(PasswordResetRequest request);
}
