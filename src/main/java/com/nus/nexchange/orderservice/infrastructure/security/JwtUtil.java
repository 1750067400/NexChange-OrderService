package com.nus.nexchange.orderservice.infrastructure.security;

import com.nus.nexchange.orderservice.application.security.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${JWT_SECRET}")
    private String secretKey;
//    private final int expirationTime = 1000 * 60 * 60;

    private final RedisService redisService;

    @Autowired
    public JwtUtil(RedisService redisService) {
        this.redisService = redisService;
    }

//    public long getExpirationTime() {
//        return expirationTime;
//    }

//    public String generateToken(String username) {
//        Map<String, Object> claims = new HashMap<>();
//        String token = createToken(claims, username);
//
//        redisService.saveToken(token, username, expirationTime);
//        return token;
//    }
//
//    private String createToken(Map<String, Object> claims, String subject) {
//        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 1hour expired
//                .signWith(key, SignatureAlgorithm.HS256)  // secret & algorithm
//                .compact();
//    }

    public boolean validateToken(String token) {
        if (!redisService.isTokenPresent(token)) {
            return false;
        }

        return !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
