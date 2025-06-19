package com.mphasis.rmonboarding.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.mphasis.rmonboarding.dto.LoginOtpRequest;
import com.mphasis.rmonboarding.dto.LoginOtpVerificationRequest;

@FeignClient(name = "sign-in-otp-service", url = "http://localhost:8080")
public interface SignInOtpClient {

    @PostMapping("api/otp/send")
    String sendOtp(@RequestBody LoginOtpRequest request);

    @PostMapping("api/otp/verify")
    String verifyOtp(@RequestBody LoginOtpVerificationRequest request);
}


