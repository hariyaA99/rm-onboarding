package com.mphasis.rmonboarding.repository;

import com.mphasis.rmonboarding.entity.SignUpRelationshipManager;
import com.mphasis.rmonboarding.entity.ValidateRMUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidateRMUserRepository extends CrudRepository<SignUpRelationshipManager, String> {

    @Query("SELECT r.email FROM SignUpRelationshipManager r WHERE r.rmCode = :rmCode")
    String findEmailByRmCode(@Param("rmCode") String rmCode);
}
