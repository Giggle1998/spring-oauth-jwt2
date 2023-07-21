package com.crit.oauthjwt2.service;

import com.crit.oauthjwt2.common.security.SecurityUtil;
import com.crit.oauthjwt2.dto.*;
import com.crit.oauthjwt2.entity.User;
import com.crit.oauthjwt2.entity.UserRepository;
import com.crit.oauthjwt2.enumType.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class KakaoRequestService implements RequestService {
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String TOKEN_URI;

    @Override
    public SignInResponse redirect(TokenRequest tokenRequest) {
        System.out.println(tokenRequest.getRegistrationId());
        System.out.println("=========getToken 중==============");
        TokenResponse tokenResponse = getToken(tokenRequest);
        System.out.println("=========getUserInfo 중==============");
        KakaoUserInfo kakaoUserInfo = getUserInfo(tokenResponse.getAccessToken());

        Long id = kakaoUserInfo.getId();
        String nickname = kakaoUserInfo.getKakaoAccount().getProfile().getNickname();
        String email = kakaoUserInfo.getKakaoAccount().getEmail();


        // 기존 유저 정보가 존재하면
        if(userRepository.existsById(String.valueOf(kakaoUserInfo.getId()))){
            String accessToken = securityUtil.createAccessToken(
                    String.valueOf(kakaoUserInfo.getId()), AuthProvider.KAKAO, tokenResponse.getAccessToken());
            String refreshToken = securityUtil.createRefreshToken(
                    String.valueOf(kakaoUserInfo.getId()), AuthProvider.KAKAO, tokenResponse.getRefreshToken());
            return SignInResponse.builder()
                    .authProvider(AuthProvider.KAKAO)
                    .id(String.valueOf(id))
                    .nickname(nickname)
                    .email(email)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            SignInResponse signInResponse = SignInResponse.builder()
                    .authProvider(AuthProvider.KAKAO)
                    .id(String.valueOf(id))
                    .nickname(nickname)
                    .email(email)
                    .build();
            User userEntity = signInResponse.toEntity();
            userRepository.save(userEntity);
            return signInResponse;
        }
    }

    @Override
    public TokenResponse getToken(TokenRequest tokenRequest) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", GRANT_TYPE);
        formData.add("redirect_uri", REDIRECT_URI);
        formData.add("client_id", CLIENT_ID);
        formData.add("code", tokenRequest.getCode());
        System.out.println(GRANT_TYPE);
        System.out.println(REDIRECT_URI);
        System.out.println(CLIENT_ID);
        System.out.println(tokenRequest.getCode());
        return webClient.mutate()
                .baseUrl(TOKEN_URI)
                .build()
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }

    @Override
    public KakaoUserInfo getUserInfo(String accessToken) {
        return webClient.mutate()
                .baseUrl("https://kapi.kakao.com")
                .build()
                .get()
                .uri("/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KakaoUserInfo.class)
                .block();
    }

    @Override
    public TokenResponse getRefreshToken(String provider, String refreshToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", CLIENT_ID);
        formData.add("refresh_token", refreshToken);

        return webClient.mutate()
                .baseUrl("https://kauth.kakao.com")
                .build()
                .post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }
}
