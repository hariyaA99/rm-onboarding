package com.mphasis.rmonboarding.repository;

import com.mphasis.rmonboarding.entity.SignUpRelationshipManager;
import com.mphasis.rmonboarding.entity.ValidateRMUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SignUpRepository extends CrudRepository<ValidateRMUser, String> {
    boolean existsByUsername(String username);

}
