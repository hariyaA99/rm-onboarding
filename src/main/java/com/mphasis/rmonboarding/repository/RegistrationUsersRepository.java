package com.mphasis.rmonboarding.repository;


import com.mphasis.rmonboarding.entity.RegistrationUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationUsersRepository extends JpaRepository<RegistrationUsers, String> {
    boolean existsByUsername(String username);
}

