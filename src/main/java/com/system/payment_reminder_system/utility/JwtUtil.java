package com.system.payment_reminder_system.utility;

import com.system.payment_reminder_system.entity.User;
import com.system.payment_reminder_system.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {


    @Autowired
    private UserRepository userRepository;



    private final long EXPIRATION_TIME = 1000*60*60; // 1 hour
    @Value("${JWT_SECRET}")
    private String Secret;
    private SecretKey key;

    @PostConstruct
    public void init() {

        if (Secret == null || Secret.length() < 32) {
            throw new RuntimeException("JWT_SECRET is missing or too short (must be 32+ chars)");
        }
        this.key = Keys.hmacShaKeyFor(Secret.getBytes());
    }

    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }

    }


    public Long getUserIdFromEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("USER NOT FOUND!!"));

        return user.getUserId();

    }

}
