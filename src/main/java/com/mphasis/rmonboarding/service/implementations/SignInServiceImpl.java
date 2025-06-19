package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.entity.SignInUsers;
import com.mphasis.rmonboarding.repository.SignInRepository;
import com.mphasis.rmonboarding.service.interfaces.SignInSessionDetailsService;
import com.mphasis.rmonboarding.service.interfaces.SignInService;
import com.mphasis.rmonboarding.util.JwtUtil;
import com.mphasis.rmonboarding.feign.SignInOtpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import com.mphasis.rmonboarding.config.PasswordConfig;
@Service
public class SignInServiceImpl implements SignInService {

    private final SignInRepository signInRepository;
    private final JwtUtil jwtUtil;
    private final SignInSessionDetailsService signInSessionDetailsService;
    private final SignInOtpClient signInOtpClient; // ✅ inject Feign client

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SignInServiceImpl(
            SignInRepository signInRepository,
            JwtUtil jwtUtil,
            SignInSessionDetailsService signInSessionDetailsService,
            SignInOtpClient signInOtpClient) { // ✅ constructor injection
        this.signInRepository = signInRepository;
        this.jwtUtil = jwtUtil;
        this.signInSessionDetailsService = signInSessionDetailsService;
        this.signInOtpClient = signInOtpClient;
    }

    @Override
    public SignInUsers findByEmail(String email) {
        return signInRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public SignInUsers validateUser(String email, String rawPassword) {
        Optional<SignInUsers> optionalUser = signInRepository.findByUsername(email);

        if (optionalUser.isEmpty()) {
            // Email does not exist
            throw new RuntimeException("Invalid email or password");
        }

        SignInUsers user = optionalUser.get();

        if (user.isBlocked()) {
            throw new RuntimeException("BLOCKED");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            // Increment failed attempts
//            System.out.println("User entered: "+passwordEncoder.encode(rawPassword));
//            System.out.println("DB password: "+user.getPasswordHash());
            int attempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(attempts);

            // Check if limit is reached
            if (attempts >= 3) {
                user.setBlocked(true);
                user.setModifiedOn(LocalDateTime.now());
                signInRepository.save(user);
                throw new RuntimeException("BLOCKED"); // Custom message
            }else {
                signInRepository.save(user);
                throw new RuntimeException("INVALID_PASSWORD");
            }
            // Save after incrementing
        }


        // ✅ Correct password
        user.setFailedLoginAttempts(0); // Reset attempts
        signInRepository.save(user);    // Save the reset

        return user;
    }



    @Override
    public void handleFailedLogin(SignInUsers user) {
        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);

        if (attempts >= 3) {
            user.setBlocked(true);
            user.setModifiedOn(LocalDateTime.now());
        }

        signInRepository.save(user);
    }

}
