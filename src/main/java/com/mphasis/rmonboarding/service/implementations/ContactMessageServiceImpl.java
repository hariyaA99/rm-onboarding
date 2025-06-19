package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.dto.ContactMessageRequest;
import com.mphasis.rmonboarding.entity.ContactMessage;
import com.mphasis.rmonboarding.repository.ContactMessageRepository;
import com.mphasis.rmonboarding.service.interfaces.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository repository;

    @Override
    public ContactMessage saveMessage(ContactMessageRequest request) {
        ContactMessage message = ContactMessage.builder()
                .name(request.getName())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .emailType(ContactMessage.EmailType.leads)  // ✅ Add this
                .build();
        return repository.save(message);
    }

}
