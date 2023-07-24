package com.crit.oauthjwt2.controller;

import com.crit.oauthjwt2.dto.*;
import com.crit.oauthjwt2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
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

    /*
    ** 로그아웃
    ** 프론트에서 access, refresh token을 전달받고 로그아웃 처리
    ** 프론트에서도 store에 저장된 token 정보를 삭제
     */
    @PostMapping("/logout")
    public String logOut(@RequestBody LogOutRequestDto logOutRequestDto) {
        userService.logOut(logOutRequestDto);
        return "로그아웃 완료";
    }
}