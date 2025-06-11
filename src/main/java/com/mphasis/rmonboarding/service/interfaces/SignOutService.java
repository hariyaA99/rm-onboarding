package com.mphasis.rmonboarding.service.interfaces;

import com.mphasis.rmonboarding.entity.SignOutSessionDetails;

import java.util.Optional;

public interface SignOutService {
    boolean signOut(String token);
    boolean isTokenActive(String token);
    Optional<SignOutSessionDetails> getSessionByToken(String token);
}
