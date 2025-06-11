package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.entity.SignInSessionDetails;
import com.mphasis.rmonboarding.repository.SignInSessionDetailsRepository;
import com.mphasis.rmonboarding.service.interfaces.SignInSessionDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SignInSessionDetailsServiceImpl implements SignInSessionDetailsService {

    private final SignInSessionDetailsRepository repository;

    public SignInSessionDetailsServiceImpl(SignInSessionDetailsRepository repository) {
        this.repository = repository;
    }




    @Override
    public void saveSession(String username, String jwtToken) {
        SignInSessionDetails session = new SignInSessionDetails();
        session.setUsername(username);
        session.setToken(jwtToken);
        repository.save(session);
    }
}
