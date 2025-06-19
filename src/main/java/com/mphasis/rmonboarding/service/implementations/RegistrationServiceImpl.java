package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.entity.RegistrationUsers;
import com.mphasis.rmonboarding.service.interfaces.RegistrationService;
import com.mphasis.rmonboarding.dto.RegistrationDTO;
import com.mphasis.rmonboarding.entity.RegistrationRelationshipManager;
import com.mphasis.rmonboarding.exception.PasswordMismatchException;
import com.mphasis.rmonboarding.exception.UserNotFoundException;
import com.mphasis.rmonboarding.repository.RegistrationRelationshipManagerRepository;
import com.mphasis.rmonboarding.repository.RegistrationUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationUsersRepository registrationUsersRepository;

    @Autowired
    private RegistrationRelationshipManagerRepository rmRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(RegistrationDTO dto) {
        String email = dto.getEmail().trim().toLowerCase();

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords does not match");
        }

        RegistrationRelationshipManager rm = rmRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User does not exists"));

        if (registrationUsersRepository.existsByUsername(email)) {
            throw new IllegalStateException("User already exists");
        }

        RegistrationUsers user = new RegistrationUsers();
        user.setRmCode(rm.getRmCode());
        user.setUsername(email);
        user.setCreatedBy(rm.getName());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        registrationUsersRepository.save(user);
    }
}


