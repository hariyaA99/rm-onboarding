package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.dto.SignUpOtpRequest;
import com.mphasis.rmonboarding.entity.SignUpRelationshipManager;
import com.mphasis.rmonboarding.exception.RMCodeNotFoundException;
import com.mphasis.rmonboarding.exception.RMCodeValidationException;
import com.mphasis.rmonboarding.feign.SignUpOtpClient;
import com.mphasis.rmonboarding.repository.SignUpRepository;
import com.mphasis.rmonboarding.service.interfaces.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private SignUpRepository signUpRepository;

    @Autowired
    private SignUpOtpClient signUpOtpClient;

    @Override
    public boolean checkIfUserExistsByEmail(String email) {
        return signUpRepository.existsByUsername(email);
    }

    @Override
    public void sendOtp(String email) {

        signUpOtpClient.sendOtp(new SignUpOtpRequest(email)); // ✅ Sending proper DTO

    }
}
