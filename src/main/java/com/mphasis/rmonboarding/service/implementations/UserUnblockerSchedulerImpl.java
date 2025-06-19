package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.entity.SignInUsers;
import com.mphasis.rmonboarding.repository.SignInRepository;
import com.mphasis.rmonboarding.service.interfaces.UserUnblockerScheduler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserUnblockerSchedulerImpl implements UserUnblockerScheduler {

    @Autowired
    private SignInRepository signInRepository;

    // 🔁 Runs every 5 seconds
    @Scheduled(fixedRate = 5000)
    @Transactional
    public void unblockUsersAfterTimeout() {
        // Find all users who are currently blocked
        List<SignInUsers> blockedUsers = signInRepository.findAll().stream()
                .filter(SignInUsers::isBlocked)
                .toList();

        LocalDateTime now = LocalDateTime.now();

        for (SignInUsers user : blockedUsers) {
            if (user.getModifiedOn() == null) continue;

            Duration timeSinceBlocked = Duration.between(user.getModifiedOn(), now);

            // ⏳ If 30 seconds passed since user got blocked
            if (timeSinceBlocked.getSeconds() >= 120) {
                user.setBlocked(false);
                user.setFailedLoginAttempts(0);
                user.setModifiedOn(LocalDateTime.now());
                signInRepository.save(user);

                System.out.println("Unblocked user: " + user.getUsername());
            }
        }
    }
}
