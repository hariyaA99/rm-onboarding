package com.mphasis.rmonboarding.service.interfaces;

import com.mphasis.rmonboarding.dto.ContactMessageRequest;
import com.mphasis.rmonboarding.entity.ContactMessage;

public interface ContactMessageService {
    ContactMessage saveMessage(ContactMessageRequest request);
}
