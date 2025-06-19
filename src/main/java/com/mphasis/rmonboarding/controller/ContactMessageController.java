package com.mphasis.rmonboarding.controller;

import com.mphasis.rmonboarding.dto.ContactMessageRequest;
import com.mphasis.rmonboarding.entity.ContactMessage;
import com.mphasis.rmonboarding.service.interfaces.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class ContactMessageController {

    private final ContactMessageService service;

    @PostMapping("/send")
    public ResponseEntity<ContactMessage> sendMessage(@RequestBody ContactMessageRequest request) {
        ContactMessage saved = service.saveMessage(request);
        return ResponseEntity.ok(saved);
    }
}
