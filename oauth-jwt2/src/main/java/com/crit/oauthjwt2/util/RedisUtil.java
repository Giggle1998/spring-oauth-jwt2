package com.crit.oauthjwt2.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    //refreshtoken 저장
    public void saveRefreshToken(String email, String refreshToken) {
        redisTemplate.opsForHash().put("email:token", email, refreshToken);
        redisTemplate.expire("email:token", 2, TimeUnit.HOURS);
    }

    //refreshtoken 조회
    public String getRefreshTokens(String email) {
        return (String) redisTemplate.opsForHash().get("email:token", email);
    }

    public boolean isMatchToken(String email, String refreshToken){
        String redisToken = getRefreshTokens(email);
        return redisToken != null && redisToken.equals(refreshToken);
    }

}
