package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.dto.RmCodeCheckResponse;
import com.mphasis.rmonboarding.entity.ValidateRMUser;
import com.mphasis.rmonboarding.repository.ValidateRMUserRepository;
import com.mphasis.rmonboarding.service.interfaces.ValidateRMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidateRMUserServiceImpl implements ValidateRMUserService {

    @Autowired
    private ValidateRMUserRepository validateRMUserRepository;

    @Override
    public String getEmailByRmCode(String rmCode) {
        return validateRMUserRepository.findEmailByRmCode(rmCode);
    }
}
