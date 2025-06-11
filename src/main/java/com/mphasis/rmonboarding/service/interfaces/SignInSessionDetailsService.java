package com.mphasis.rmonboarding.service.interfaces;

public interface SignInSessionDetailsService {
    void saveSession(String username, String jwtToken);
}
