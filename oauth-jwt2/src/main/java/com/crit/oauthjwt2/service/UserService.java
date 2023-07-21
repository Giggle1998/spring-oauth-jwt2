package com.crit.oauthjwt2.service;

import com.crit.oauthjwt2.common.exception.BadRequestException;
import com.crit.oauthjwt2.dto.SignUpRequest;
import com.crit.oauthjwt2.entity.User;
import com.crit.oauthjwt2.entity.UserRepository;
import com.crit.oauthjwt2.enumType.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public String createUser(SignUpRequest signUpRequest){
        if(userRepository.existsByIdAndAuthProvider(signUpRequest.getId(), signUpRequest.getAuthProvider())){
            throw new BadRequestException("aleady exist user");
        }

        return userRepository.save(
                User.builder()
                        .id(signUpRequest.getId())
                        .nickname(signUpRequest.getNickname())
                        .email(signUpRequest.getEmail())
                        .profileImageUrl(signUpRequest.getProfileImageUrl())
                        .role(Role.USER)
                        .authProvider(signUpRequest.getAuthProvider())
                        .build()).getId();
    }
}
