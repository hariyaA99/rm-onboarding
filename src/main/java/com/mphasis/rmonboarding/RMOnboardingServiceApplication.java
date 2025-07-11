package com.mphasis.rmonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.mphasis.rmonboarding.feign")
@EnableScheduling
@ComponentScan("com.mphasis.rmonboarding")
public class RMOnboardingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RMOnboardingServiceApplication.class, args);
	}

}
