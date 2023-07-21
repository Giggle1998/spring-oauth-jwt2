package com.crit.oauthjwt2.controller;

import com.crit.oauthjwt2.dto.SignInResponse;
import com.crit.oauthjwt2.dto.TokenRequest;
import com.crit.oauthjwt2.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login/oauth2/code/{registrationId}")
    public ResponseEntity<SignInResponse> redirect(
            @PathVariable("registrationId") String registrationId
            , @RequestParam("code") String code
            , @RequestParam("state") String state) {
        System.out.println("=======================");
        System.out.println(registrationId + code + state);
        System.out.println("=======================");
        return ResponseEntity.ok(
                authService.redirect(
                    TokenRequest.builder()
                            .registrationId(registrationId)
                            .code(code)
                            .state(state)
                            .build()));
    }

    @PostMapping("/auth/token")
    public ResponseEntity<SignInResponse> refreshToken(@RequestBody TokenRequest tokenRequest){
        return ResponseEntity.ok(authService.refreshToken(tokenRequest));
    }
}