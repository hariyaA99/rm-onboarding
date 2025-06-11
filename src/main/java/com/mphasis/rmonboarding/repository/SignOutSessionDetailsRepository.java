package com.mphasis.rmonboarding.repository;

import com.mphasis.rmonboarding.entity.SignOutSessionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignOutSessionDetailsRepository extends JpaRepository<SignOutSessionDetails, String> {

    @Query("SELECT s FROM SignOutSessionDetails s WHERE s.token = :token AND s.token IS NOT NULL")
    Optional<SignOutSessionDetails> findByActiveToken(@Param("token") String token);

    @Query("SELECT s FROM SignOutSessionDetails s WHERE s.username = :username AND s.token = :token AND s.token IS NOT NULL")
    Optional<SignOutSessionDetails> findByUsernameAndToken(@Param("username") String username, @Param("token") String token);

    Optional<SignOutSessionDetails> findByUsername(String username);

    @Query("SELECT COUNT(s) > 0 FROM SignOutSessionDetails s WHERE s.token = :token AND s.token IS NOT NULL")
    boolean existsByActiveToken(@Param("token") String token);

    @Query("SELECT COUNT(s) > 0 FROM SignOutSessionDetails s WHERE s.username = :username AND s.token = :token AND s.token IS NOT NULL")
    boolean existsByUsernameAndToken(@Param("username") String username, @Param("token") String token);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}