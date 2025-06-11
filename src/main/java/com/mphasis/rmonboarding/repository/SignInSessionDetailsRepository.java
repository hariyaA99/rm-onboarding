package com.mphasis.rmonboarding.repository;

import com.mphasis.rmonboarding.entity.SignInSessionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignInSessionDetailsRepository extends JpaRepository<SignInSessionDetails, Integer> {
}
