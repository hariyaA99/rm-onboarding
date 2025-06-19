package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.service.interfaces.SessionCleanup;

import com.mphasis.rmonboarding.entity.SignOutSessionDetails;
import com.mphasis.rmonboarding.repository.SignOutSessionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SessionCleanupImpl implements SessionCleanup {
    @Autowired
    private SignOutSessionDetailsRepository sessionRepo;

    // 🔁 Runs every 10 seconds
    @Scheduled(fixedRate = 10000)  // 10,000 ms = 10 seconds
    public void cleanupExpiredSessions() {
        LocalDateTime expiryThreshold = LocalDateTime.now().minusMinutes(30);  // Sessions older than 30 min
        List<SignOutSessionDetails> expiredSessions = sessionRepo.findByCreatedAtBefore(expiryThreshold);

        if (!expiredSessions.isEmpty()) {
            sessionRepo.deleteAll(expiredSessions);
            System.out.println("🧹 Deleted " + expiredSessions.size() + " expired sessions at " + LocalDateTime.now());
        }
    }
}







