package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.dto.PasswordResetRequest;
import com.mphasis.rmonboarding.entity.PasswordResetRelationshipManager;
import com.mphasis.rmonboarding.entity.PasswordResetUsers;
import com.mphasis.rmonboarding.repository.PasswordResetRelationshipManagerRepository;
import com.mphasis.rmonboarding.repository.PasswordResetUserRepository;
import com.mphasis.rmonboarding.service.interfaces.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private PasswordResetUserRepository passwordResetUserRepository;

    @Autowired
    private PasswordResetRelationshipManagerRepository passwordResetRelationshipManagerRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<String> resetPassword(PasswordResetRequest request) {
        String email = request.getEmail();

        // Step 1: Check password match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        // Step 2: Fetch RM using email
        Optional<PasswordResetRelationshipManager> rmOpt = passwordResetRelationshipManagerRepository.findByEmailIgnoreCase(email);
        if (rmOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found in Relationship Manager database");
        }

        PasswordResetRelationshipManager rm = rmOpt.get(); // ✅ contains name and rmCode
        String rmCode = rm.getRmCode();
        String modifiedByName = rm.getName(); // ✅ the name to use in 'modifiedBy'

        // Step 3: Fetch user by rmCode and update
        Optional<PasswordResetUsers> userOpt = passwordResetUserRepository.findByRmCode(rmCode);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("PasswordResetUsers not found for the given RM code");
        }

        PasswordResetUsers passwordResetUsers = userOpt.get();
        passwordResetUsers.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        passwordResetUsers.setPasswordUpdatedAt(LocalDateTime.now());
        passwordResetUsers.setModifiedOn(LocalDateTime.now());
        passwordResetUsers.setModifiedBy(modifiedByName); // ✅ this sets the name of RM who changed password

        passwordResetUserRepository.save(passwordResetUsers);

        return ResponseEntity.ok("Password reset successful");
    }


    // You said you're using Feign for OTP, so this can be deleted or left empty
    @Override
    public ResponseEntity<String> verifyOtp(PasswordResetRequest request) {
        return ResponseEntity.ok("OTP verified (placeholder)");
    }
}
