// File: com.mphasis.rmonboarding.service.interfaces.RMEditService.java
package com.mphasis.rmonboarding.service.interfaces;

import com.mphasis.rmonboarding.dto.RMEditRequestDTO;
import com.mphasis.rmonboarding.entity.RegistrationRelationshipManager;

public interface RMEditService {
    RegistrationRelationshipManager editProfile(String rmCode, RMEditRequestDTO request);
}
