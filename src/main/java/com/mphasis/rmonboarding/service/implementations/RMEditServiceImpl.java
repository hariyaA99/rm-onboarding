// File: com.mphasis.rmonboarding.service.implementations.RMEditServiceImpl.java
package com.mphasis.rmonboarding.service.implementations;

import com.mphasis.rmonboarding.dto.RMEditRequestDTO;
import com.mphasis.rmonboarding.entity.RegistrationRelationshipManager;
import com.mphasis.rmonboarding.exception.RMNotFoundException;
import com.mphasis.rmonboarding.repository.RegistrationRelationshipManagerRepository;
import com.mphasis.rmonboarding.service.interfaces.RMEditService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RMEditServiceImpl implements RMEditService {

    private final RegistrationRelationshipManagerRepository relationshipManagerRepository;

    public RMEditServiceImpl(RegistrationRelationshipManagerRepository relationshipManagerRepository) {
        this.relationshipManagerRepository = relationshipManagerRepository;
    }

    @Override
    public RegistrationRelationshipManager editProfile(String rmCode, RMEditRequestDTO request) {
        RegistrationRelationshipManager rm = relationshipManagerRepository.findById(rmCode)
                .orElseThrow(() -> new RMNotFoundException("Relationship Manager not found with code: " + rmCode));

        rm.setName(request.getName());
        rm.setEmail(request.getEmail());
        rm.setPhone(request.getPhone());
        rm.setAlternatePhone(request.getAlternatePhone());
        rm.setDob(request.getDob());
        rm.setLocation(request.getLocation());
        rm.setPhotoBase64(request.getPhotoBase64());
        rm.setModifiedBy(request.getModifiedBy());
        rm.setModifiedOn(LocalDateTime.now());

        return relationshipManagerRepository.save(rm);
    }
}
