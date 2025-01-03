package com.nus.nexchange.orderservice.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToken(String token, String username, long expirationTime) {
        redisTemplate.opsForValue().set(token, username, expirationTime, TimeUnit.MILLISECONDS);
    }

//    public String getUsernameFromToken(String token) {
//        return redisTemplate.opsForValue().get(token);
//    }
//
//    public void deleteToken(String token) {
//        redisTemplate.delete(token);
//    }

    public boolean isTokenPresent(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
