package com.crit.oauthjwt2.controller;

import com.crit.oauthjwt2.dto.SignUpRequest;
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

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(userService.createUser(signUpRequest));
    }
}