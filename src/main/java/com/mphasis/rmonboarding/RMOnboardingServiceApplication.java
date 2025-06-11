package com.mphasis.rmonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.mphasis.rmonboarding.feign")
@ComponentScan("com.mphasis.rmonboarding")
public class RMOnboardingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RMOnboardingServiceApplication.class, args);
	}

}
