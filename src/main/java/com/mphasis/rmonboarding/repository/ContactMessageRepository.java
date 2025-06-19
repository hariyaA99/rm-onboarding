package com.mphasis.rmonboarding.repository;

import com.mphasis.rmonboarding.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer> {
}
