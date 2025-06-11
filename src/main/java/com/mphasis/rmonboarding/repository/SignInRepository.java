package com.mphasis.rmonboarding.repository;

import com.mphasis.rmonboarding.entity.SignInUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignInRepository extends JpaRepository<SignInUsers, String> {
    Optional<SignInUsers> findByUsername(String username);
}
