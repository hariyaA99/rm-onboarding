// File: com.mphasis.rmonboarding.repository.RegistrationRelationshipManagerRepository.java
package com.mphasis.rmonboarding.repository;

import com.mphasis.rmonboarding.entity.RegistrationRelationshipManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRelationshipManagerRepository extends JpaRepository<RegistrationRelationshipManager, String> {

    Optional<RegistrationRelationshipManager> findByEmailIgnoreCase(String email);

    // You can add custom query methods if needed, for example:
    // Optional<RegistrationRelationshipManager> findByPhone(String phone);
}
