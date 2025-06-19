package com.mphasis.rmonboarding.repository;

import com.mphasis.rmonboarding.entity.PasswordResetRelationshipManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRelationshipManagerRepository extends JpaRepository<PasswordResetRelationshipManager, String> {
    Optional<PasswordResetRelationshipManager> findByEmailIgnoreCase(String email);
}



