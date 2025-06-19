package com.mphasis.rmonboarding.service.interfaces;

import com.mphasis.rmonboarding.dto.RmCodeCheckResponse;

public interface ValidateRMUserService {
    String getEmailByRmCode(String rmCode);
}
