package com.mphasis.rmonboarding.feign;

import com.mphasis.rmonboarding.dto.SignUpOtpRequest;
import com.mphasis.rmonboarding.dto.SignUpVerifyOtpRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sign-up-otp-service", url = "http://localhost:8080")
public interface SignUpOtpClient {

    @PostMapping("/api/otp/send")
    String sendOtp(@RequestBody SignUpOtpRequest request);

    @PostMapping("/api/otp/verify")
    String verifyOtp(@RequestBody SignUpVerifyOtpRequest request);
}
