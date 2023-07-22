package com.crit.oauthjwt2.dto;

import com.crit.oauthjwt2.entity.User;
import com.crit.oauthjwt2.enumType.AuthProvider;
import com.crit.oauthjwt2.enumType.Role;
import com.crit.oauthjwt2.util.PasswordUtil;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthSignInResponse {
    private AuthProvider authProvider;
//    private KakaoUserInfo kakaoUserInfo;
//    private NaverUserInfo naverUserInfo;
//    private GoogleUserInfo googleUserInfo;
    private String id;
    private String nickname;
    private String email;
    private String accessToken;
    private String refreshToken;

    @Builder
    public OAuthSignInResponse(
            AuthProvider authProvider
            ,String id
            ,String nickname
            ,String email
            ,String accessToken
            ,String refreshToken
    ){
        this.authProvider = authProvider;
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public User toEntity() {
        // OAuth는 따로 password 정보가 없기 때문에 임의로 생성
        String password = PasswordUtil.generateRandomPassword();
        return User.builder()
                .id(id)
                .authProvider(authProvider)
                .nickname(nickname)
                .password(password)
                .email(email)
                .role(Role.USER)
                .build();
    }

}
