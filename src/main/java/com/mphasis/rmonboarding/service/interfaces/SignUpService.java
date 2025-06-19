package com.mphasis.rmonboarding.service.interfaces;

import com.mphasis.rmonboarding.entity.SignUpRelationshipManager;

public interface SignUpService {
    boolean checkIfUserExistsByEmail(String email);
    void sendOtp(String email);
}