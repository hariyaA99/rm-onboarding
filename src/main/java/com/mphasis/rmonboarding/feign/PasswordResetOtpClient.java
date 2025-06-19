package com.mphasis.rmonboarding.feign;


import com.mphasis.rmonboarding.dto.PasswordResetOtpRequest;
import com.mphasis.rmonboarding.dto.PasswordResetOtpVerification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "forgot-password-otp-service", url = "http://localhost:8080")
public interface PasswordResetOtpClient {

    @PostMapping("/api/otp/send")
    String sendOtp(@RequestBody PasswordResetOtpRequest request);

    @PostMapping("/api/otp/verify")
    String verifyOtp(@RequestBody PasswordResetOtpVerification request);
}





