package com.mphasis.rmonboarding.service.interfaces;

import com.mphasis.rmonboarding.entity.SignInUsers;

public interface  SignInService {
    SignInUsers validateUser(String email, String password);
    SignInUsers findByEmail(String email);
    void handleFailedLogin(SignInUsers user);
}
