package com.mphasis.rmonboarding.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}

