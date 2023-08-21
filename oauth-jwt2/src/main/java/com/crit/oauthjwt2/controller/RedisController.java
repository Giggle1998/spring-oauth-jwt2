package com.crit.oauthjwt2.controller;

import com.crit.oauthjwt2.common.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedisController {
    private final RedisTemplate<String, Object> redisTemplate;
    private final SecurityUtil securityUtil;

    @GetMapping("/redis")
    public ResponseEntity<Object> getRedis(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String userId = (String)securityUtil.get(token).get("userId");
        Object refreshToken = redisTemplate.opsForValue().get("RefreshToken:"+userId);
        return ResponseEntity.ok(refreshToken);

    }

}
