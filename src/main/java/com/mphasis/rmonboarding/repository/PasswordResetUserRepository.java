package com.mphasis.rmonboarding.repository;

import com.mphasis.rmonboarding.entity.PasswordResetUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetUserRepository extends JpaRepository<PasswordResetUsers, String> {
    boolean existsByUsername(String username);
    Optional<PasswordResetUsers> findByRmCode(String rmCode);
    Optional<PasswordResetUsers> findByUsername(String username);
}





