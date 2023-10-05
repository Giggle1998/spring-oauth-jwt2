package com.crit.oauthjwt2.controller;

import com.crit.oauthjwt2.common.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final SecurityUtil securityUtil;

    @GetMapping("/test")
    public String getTest(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        String userId = (String) securityUtil.get(token).get("userId");
        System.out.println("===================testing=====================");
        System.out.println("=========" + userId + "=============");
        return "success Test";
    }

    @GetMapping("/test2")
    public String getTest2(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        String provider = (String) securityUtil.get(token).get("provider");
        System.out.println("===================testing=====================");
        System.out.println("=========" + provider + "=============");
        return "success Test2";
    }
}
