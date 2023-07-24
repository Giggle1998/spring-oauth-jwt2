package com.crit.oauthjwt2.controller;

import com.crit.oauthjwt2.dto.LogInRequestDto;
import com.crit.oauthjwt2.dto.LogInResponseDto;
import com.crit.oauthjwt2.dto.SignUpRequest;
import com.crit.oauthjwt2.dto.SignUpRequestDto;
import com.crit.oauthjwt2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws Exception {
        return ResponseEntity.ok(userService.signUp(signUpRequestDto));
    }
    // 일반 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<LogInResponseDto> logIn(@RequestBody LogInRequestDto logInRequestDto) throws Exception{
        return ResponseEntity.ok(userService.logIn(logInRequestDto));
    }
//    @PostMapping("/user")
//    public ResponseEntity<String> createUser(@RequestBody SignUpRequest signUpRequest) {
//        return ResponseEntity.ok(userService.createUser(signUpRequest));
//    }

}